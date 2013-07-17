package com.example.framework;

class Record implements java.io.Serializable
{
        long rowid;
        String groupid;
        String key, value, user, datatype, synched;
        long timestamp;
}