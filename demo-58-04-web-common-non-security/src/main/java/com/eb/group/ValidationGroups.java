package com.eb.group;

public final class ValidationGroups {
    public static final class Req {
        public interface Query {}
        public interface Create {}
        public interface Update {}
        public interface Delete {}
        public interface Others {}
    }

    public static final class Sql {
        public interface Insert {}
        public interface Update {}
        public interface Delete {}
    }
}
