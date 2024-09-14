package com.eb.tgbot;

import com.eb.business.dto.vip.req.VipAccountQueryReqDto;
import com.eb.business.dto.vip.req.VipInfoQueryReqDto;
import com.eb.business.dto.vip.rsp.VipAccountRspDto;
import com.eb.business.service.VipBankAccountService;
import com.eb.business.service.VipInfoService;
import com.eb.business.service.VipService;
import com.eb.business.service.VipTppService;
import com.eb.config.tgbot.BotServerConfig;
import com.eb.constant.enums.EnableStatusEnums;
import com.eb.constant.enums.FinancialCategoryEnums;
import com.eb.constant.enums.ReviewStatusEnums;
import com.eb.event.MqMessageEvent;
import com.eb.mp.mysql.entity.business.VipInfoEntity;
import com.eb.mq.consumer.dto.business.NoticeToAgentMqDto;
import com.eb.mq.product.dto.MqMessageProductDto;
import com.eb.mq.product.dto.business.SelectFinancialNoticeMqDto;
import com.eb.mq.product.dto.enums.MqMessageEventCategoryEnums;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.GetMe;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetMeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class BotManager implements ApplicationRunner {
    private final BotServerConfig botServerConfig;
    private final VipInfoService  vipInfoService;
    private final VipService      vipService;
    private final VipTppService   vipTppService;
    private final VipBankAccountService vipBankAccountService;
    private final ApplicationContext applicationContext;
    TelegramBot    tgBot;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (!botServerConfig.isEnable())
        {
            log.info("telegramBot Server没有启用。");
        }
        // 注册bot
        tgBot = new TelegramBot(botServerConfig.getToken());

        GetMe me = new GetMe();
        log.info("正在向telegram服务器注册bot,请稍等。token:{}", botServerConfig.getToken());

        GetMeResponse re = tgBot.execute(me);

        if (re.isOk()) {
            log.info("telegram bot注册成功 name:{}, username: @{}", re.user().firstName() + " " + re.user().lastName(), re.user().username());
        } else {
            String errorMessage = String.format("telegram bot注册失败，程序退出。请检查token是否有效：%s, 错误信息：error code: %d description: %s", botServerConfig.getToken(), re.errorCode(), re.description());
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }

        // 注册菜单命令
        RegisterBotCommands();

        try {
            // 启动消息处理
            tgBot.setUpdatesListener(updates -> {
                updates.forEach(update -> {
                    log.info("telegram bot 收到消息:{}", update);

                    try {
                        // 处理内联键盘回调消息（用在选择收款卡）
                        if (update.callbackQuery() != null) {
                            handleCallbackQuery(update);
                            //SaveChatId(update.callbackQuery().maybeInaccessibleMessage().chat().id(), update.callbackQuery().maybeInaccessibleMessage().chat().username());
                            return;
                        }

                        // 处理文本指令
                        if (update.message() != null && update.message().text() != null) {
                            // 处理文本指令
                            handleCommand(update);
                            return;
                        }

                    } catch (Exception e) {
                        log.error("telegram bot消息处理异常:{}", e.getMessage());
                    }

                });
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            });
        } catch (Exception e) {
            log.error("telegram bot消息监听器异常:{}", e.getMessage());
        }
    }

    // 注册菜单命令
    private void RegisterBotCommands() {
        BotCommand[] commands = {
                // 推荐把/start放最后
                new BotCommand(botServerConfig.getCommand().getBalanceCommand(), botServerConfig.getCommand().getBalanceDescription()),
                new BotCommand(botServerConfig.getCommand().getContactCommand(), botServerConfig.getCommand().getContactDescription()),
                new BotCommand(botServerConfig.getCommand().getHelpCommand(), botServerConfig.getCommand().getHelpDescription()),
                new BotCommand(botServerConfig.getCommand().getSelectCardCommand(), botServerConfig.getCommand().getSelectCardDescription()),
                new BotCommand(botServerConfig.getCommand().getStartCommand(), botServerConfig.getCommand().getStartDescription()),
        };
        SetMyCommands request = new SetMyCommands(commands);
        BaseResponse response = tgBot.execute(request);
        if (response.isOk()) {
            log.info("telegram bot初始化完成");
        } else {
            log.info("telegram bot菜单注册失败: errorcode:{} msg:{}", response.errorCode(), response.description());
            String errorMessage = String.format("telegram bot菜单注册失败: error code: %d description: %s", response.errorCode(), response.description());
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
    }

    // 保存chatID
    private void SaveChatId(Long chatID, String userName) {
        try
        {
            vipInfoService.updateTgUsernameById(chatID, userName);
            log.info("telegram bot 保存用户:{} 的chatId:{}", userName, chatID);
        }
        catch (Exception e)
        {
            log.info("telegram bot 保存用户:{}的chatId:{} 异常（可能是未绑定UserName?） :{}", userName, chatID, e.getMessage());
        }
    }

    // 处理内联键盘回调消息
    private void handleCallbackQuery(Update update) {
        String callbackData = update.callbackQuery().data();  // 获取callbackData
        long chatId = update.callbackQuery().maybeInaccessibleMessage().chat().id();  // 获取用户的chatId
        String userName = update.callbackQuery().maybeInaccessibleMessage().chat().username();
        StringBuilder msgBuilder = new StringBuilder();

        // 目前只有选择收款卡业务
        try {
            // 根据callbackData处理不同的业务逻辑
            Map<String, String> callbackDataMap = parseCallbackData(callbackData);

            // 获取并转换特定的值
            String callbackCode  = callbackDataMap.get("code");
            String callbackType  = callbackDataMap.get("type");
            String callbackDtoID =  callbackDataMap.get("dtoId");
            Long dtoId = null;
            int financialCategory = 0;
            if(null == callbackCode || null == callbackType || null == callbackDtoID ) {
                log.info("telegram bot 内联键盘回调消息处理, CallbackData:{}, map解析失败", callbackData);
                msgBuilder.append(botServerConfig.getCommand().getSelectCardFailMessage());
            } else {
                try {
                    dtoId = Long.parseLong(callbackDtoID);
                    if (Objects.equals(FinancialCategoryEnums.TPP.getCode(), callbackType)) {
                        financialCategory = FinancialCategoryEnums.TPP.getCodeEnum();
                        if (vipTppService.enableTpp(callbackCode, dtoId)) {
                            vipBankAccountService.disableByCode(callbackCode);
                            msgBuilder.append(botServerConfig.getCommand().getSelectCardSuccessMessage());
                        } else {
                            msgBuilder.append(botServerConfig.getCommand().getSelectCardFailMessage());
                        }
                    } else {
                        financialCategory = FinancialCategoryEnums.BANK.getCodeEnum();
                        if (vipBankAccountService.enableBankAccount(callbackCode, dtoId)) {
                            vipTppService.disableByCode(callbackCode);
                            msgBuilder.append(botServerConfig.getCommand().getSelectCardSuccessMessage());
                        } else {
                            msgBuilder.append(botServerConfig.getCommand().getSelectCardFailMessage());
                        }
                    }

                    if ( 0!= financialCategory) {
                        // 用户选择收款卡之后，通知业务方
                        // 获取代理商手机号
                        String agentPnum = vipInfoService.queryAgentPhoneByCode(callbackCode);

                        SelectFinancialNoticeMqDto selectMqDto = new SelectFinancialNoticeMqDto()
                                .setCode(callbackCode)
                                .setPnum(agentPnum)
                                .setTableID(dtoId)
                                .setFinancialCategory(financialCategory);
                        MqMessageProductDto mqDto = new MqMessageProductDto()
                                .setMessageCategoryCode(MqMessageEventCategoryEnums.SELECT_FINANCIAL_NOTICE.getCode())
                                .setSelectFinancialNotice(selectMqDto);
                        applicationContext.publishEvent(new MqMessageEvent(mqDto));
                        log.info("telegram bot 通知业务方，tg用户 @{} 选择了收款卡号. {}", userName, selectMqDto.toString());
                    }

                } catch (Exception e) {
                    msgBuilder.setLength(0);
                    msgBuilder.append(botServerConfig.getCommand().getSelectCardFailMessage());
                    log.error("telegram bot 内联键盘回调消息处理异常, CallbackData:{}, ID:{}", callbackData, callbackDtoID);
                }
            }

            // 回复用户
            SendMessage sendMessage = new SendMessage(chatId, msgBuilder.toString());
            BaseResponse res =  tgBot.execute(sendMessage);
            log.info("telegram bot 发送内联间盘回调消息回复给tg用户 @{}:{}, 返回码:{}, 消息:{}", userName, msgBuilder, res.errorCode(), res.description());

        } catch (Exception e) {
            log.error("telegram bot 内联键盘回调消息处理异常, CallbackData:{}, 异常:{}", callbackData, e.getMessage());
        }
    }
    // 处理指令消息
    private void handleCommand(Update update) {
        String command = update.message().text();
        String tgUserName = update.message().from().username();
        String responseText;
        InlineKeyboardMarkup inlineKeyboardMarkup = null;

        // 从配置文件中获取指令值
        String balanceCommand = botServerConfig.getCommand().getBalanceCommand();
        String contactCommand = botServerConfig.getCommand().getContactCommand();
        String helpCommand = botServerConfig.getCommand().getHelpCommand();
        String startCommand = botServerConfig.getCommand().getStartCommand();
        String selectCardCommand = botServerConfig.getCommand().getSelectCardCommand();

        // TODO: 使用FunctionInterface重构
        try {
            // 查余额
            if (command.equals(balanceCommand)) {
                // 查询用户代理code
                VipInfoEntity vipInfoEntity = QueryAgentCodeByUserName(tgUserName);
                if (vipInfoEntity == null) {
                    // username没有绑定代理code
                    responseText = botServerConfig.getCommand().getNoBindCodeMessage();
                } else {
                    // 查询余额和收款信息
                    responseText = QueryBalance(vipInfoEntity);
                }

            } else if (command.equals(contactCommand)) { //查询客服联系方式
                responseText = ContactService();
            } else if (command.equals(helpCommand)) { //help
                responseText = botServerConfig.getCommand().getHelpMessage();
            } else if (command.equals(startCommand)) { //start
                // 在start的时候，保存chatID
                SaveChatId(update.message().chat().id(), update.message().from().username());
                responseText = botServerConfig.getCommand().getStartDescription();

            } else if (command.equals(selectCardCommand)) { // 查询收款卡
                // 查询用户代理code
                VipInfoEntity vipInfoEntity = QueryAgentCodeByUserName(tgUserName);
                if (vipInfoEntity == null) {
                    // username没有绑定代理code
                    responseText = botServerConfig.getCommand().getNoBindCodeMessage();
                } else {
                    // 查询收款卡
                    inlineKeyboardMarkup = QueryPaymentCard(vipInfoEntity);
                    if (inlineKeyboardMarkup.inlineKeyboard().length > 0)
                    {
                        responseText = botServerConfig.getCommand().getSelectCardMessage();
                    }
                    else {
                        responseText = botServerConfig.getCommand().getSelectNoCardMessage();
                    }
                }

            } else {
                responseText = botServerConfig.getCommand().getUnknownCommandMessage();
            }

            SendMessage response = new SendMessage(update.message().chat().id(), responseText);

            // 设置内联键盘
            if(null != inlineKeyboardMarkup && inlineKeyboardMarkup.inlineKeyboard().length > 0)
            {
                response.replyMarkup(inlineKeyboardMarkup);
            }
            BaseResponse res =  tgBot.execute(response);
            log.info("telegram bot 收到文本消息{} 回复给tg用户 @{}：{}, 返回码:{}, 消息:{}", command, tgUserName, responseText, res.errorCode(), res.description());
        }
        catch (Exception e) {
            log.error("telegram bot 文本消息{} 处理异常:{}", command, e.getMessage());
        }
    }

    // 查询用户代理code
    private VipInfoEntity QueryAgentCodeByUserName(String tgUserName)
    {
        return vipInfoService.queryEntityByTgUsername(tgUserName);
    }


    // 查询余额和收款信息
    private String QueryBalance(VipInfoEntity vipInfoEntity)
    {
        String curFinancialAccount = "null";
        String msg = String.format(botServerConfig.getCommand().getBalanceMessage(), vipInfoEntity.getCode(), vipInfoEntity.getBalance().doubleValue());

        // 查询账号列表
        List<VipAccountRspDto> vipAccountRspDtoList = vipService.accountList( new VipAccountQueryReqDto().setCode(vipInfoEntity.getCode()));
        if(vipAccountRspDtoList == null || vipAccountRspDtoList.isEmpty())
        {
            // 没有查询到
            return msg + "null";
        } else {
            for (VipAccountRspDto vipAccountRspDto : vipAccountRspDtoList) {
                if (EnableStatusEnums.ENABLE == vipAccountRspDto.getEnable()
                        && ReviewStatusEnums.APPROVED == vipAccountRspDto.getReview()) {
                    // 找到了启用的账号
                    curFinancialAccount = vipAccountRspDto.getFinancialAccount();
                    break;
                }
            }
        }

        // 拼接返回消息
        return msg + curFinancialAccount;
    }

    // 返回客服联系方式
    private String ContactService()
    {
        StringBuilder msgBuilder = new StringBuilder(botServerConfig.getCommand().getContactMessage());
        for (BotServerConfig.Contact contact : botServerConfig.getCommand().getContact()) {
            // 拼接账户信息
            msgBuilder.append(contact.getName()).append(":").append(contact.getContactLink()).append("\r\n");
        }

        return msgBuilder.toString();
    }

    // 获取收款卡
    private InlineKeyboardMarkup QueryPaymentCard(VipInfoEntity vipInfoEntity)
    {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        // 查询账号列表
        List<VipAccountRspDto> vipAccountRspDtoList = vipService.accountList( new VipAccountQueryReqDto().setCode(vipInfoEntity.getCode()));
        if (vipAccountRspDtoList == null || vipAccountRspDtoList.isEmpty()) {
            return inlineKeyboardMarkup;
        } else {
            for (VipAccountRspDto vipAccountRspDto : vipAccountRspDtoList) {
                if (ReviewStatusEnums.APPROVED == vipAccountRspDto.getReview()) {
                    // 创建内联键盘
                    StringBuilder buttonText = new StringBuilder(vipAccountRspDto.getFinancialAccount());
                    if (EnableStatusEnums.ENABLE == vipAccountRspDto.getEnable())
                    {
                        // 启用标识
                        buttonText.append(" (Enable)");
                    }

                    String callbackData = String.format("code==%s&&type==%s&&dtoId==%d", vipAccountRspDto.getCode(),vipAccountRspDto.getFinancialCategory(), vipAccountRspDto.getId());
                    InlineKeyboardButton button = new InlineKeyboardButton(buttonText.toString()).
                            callbackData( callbackData);

                    log.info("设置内类键盘：{} ,callBackData{}", buttonText.toString(), callbackData);
                    // 添加按钮到键盘
                    inlineKeyboardMarkup.addRow(button);
                }
            }
        }

        return inlineKeyboardMarkup;

    }

    // 解析 callbackData 字符串，返回一个包含键值对的 Map
    private static Map<String, String> parseCallbackData(String callbackData) {
        Map<String, String> dataMap = new HashMap<>();

        // 按 & 分割出多个键值对
        //String callbackData = String.format("code==%s&&type==%s&&dtoId==%d", "VIP123", "Savings", 456);
        String[] pairs = callbackData.split("&&");
        for (String pair : pairs) {
            // 按 = 分割出键和值
            String[] keyValue = pair.split("==");
            if (keyValue.length == 2) {
                dataMap.put(keyValue[0], keyValue[1]);  // 添加键值对到 Map
            }
        }
        return dataMap;
    }

    // 处理业务方消息
    public void handleNoticeToAgent(NoticeToAgentMqDto noticeToAgentMqDto) {

        log.info("BotManager处理业务方发来的通知消息: {}", noticeToAgentMqDto.toString());
        // 获取code对应的tg username
        List<VipInfoEntity> listVipInfo= vipInfoService.queryByConditional(new VipInfoQueryReqDto().setCode(noticeToAgentMqDto.getCode()));

        if (listVipInfo == null || listVipInfo.isEmpty()) {
            log.error("telegram bot 处理业务方消息, 没有找到AgentCode:{}对应的代理信息(VipInfo表)", noticeToAgentMqDto.getCode());
            return;
        }

        for (VipInfoEntity vipInfoEntity : listVipInfo) {
            if(null == vipInfoEntity.getTgChatId())
            {
                log.error("telegram bot 处理业务方消息, 未找到代理code[{}]对应的tg chatID", noticeToAgentMqDto.getCode());
                return;
            }

            SendMessage sendMessage = new SendMessage(vipInfoEntity.getTgChatId(), noticeToAgentMqDto.getMessage());
            BaseResponse res = tgBot.execute(sendMessage);
            log.info("telegram bot 转发业务方消息给:{}\t{},内容:{} 返回码:{}, 消息:{}", noticeToAgentMqDto.getCode(), vipInfoEntity.getTgUsername(), noticeToAgentMqDto.getMessage(), res.errorCode(), res.description());

            // 正常情况只会查出来一条记录,如果有多条,则只取第一条也没有问题。
            break;
        }
    }
}

