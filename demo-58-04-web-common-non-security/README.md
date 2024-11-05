



## 比较完整的web 服务

## i18n 国际化

> 每个语言对应在i18n 目录下面配置一个翻译文件。
>
> 前端传入需要添加请求头：`Accept-Language` = `zh-CN` 指定需要的语言
>
> 



## 请求参数字符串去除空白字符

### QUERY 请求参数

```java
// 将请求参数中所有字符串的首尾空白字符删除掉。
@ControllerAdvice
@Slf4j
public class StringTrimmerControllerAdvice {

    @InitBinder
    public void trimBinder(WebDataBinder binder) {
        // 仅适用于 @ModelAttribute 和 @RequestParam 绑定
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
```



### BODY 请求参数

```java
public class BizStringDeserializer extends StringDeserializer {
    public final static BizStringDeserializer instance = new BizStringDeserializer();

    /**
     * 反序列化，处理空白字符串
     * 这里处理的就是 @RequestBody 中的字符串
     */
    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return super.deserialize(p,ctxt).trim();
    }
}
```





