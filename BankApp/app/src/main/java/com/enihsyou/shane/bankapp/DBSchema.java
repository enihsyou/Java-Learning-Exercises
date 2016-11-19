package com.enihsyou.shane.bankapp;

public class DBSchema {
    public static final class AccountDBTable {
        public static final String NAME = "accounts";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String CARD = "card";
        }
    }

    public static final class CardDBTable {
        public static final String NAME = "cards";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NUMBER = "number";
            public static final String TYPE = "type";
            public static final String BALANCE = "balance";
            public static final String REMAIN = "remain";
        }
    }

    public static final class AccountCard {
        public static final String NAME = "account_card";

        public static final class Cols {
            public static final String KEY_ACCOUNT = "account_id";
            public static final String KEY_CARD = "card_id";
        }
    }
}
