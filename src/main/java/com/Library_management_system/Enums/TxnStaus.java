package com.Library_management_system.Enums;

public enum TxnStaus
{
    ISSUED,  //Book just issue
    FINED, //return with the fine value
    RETURNED,// There is no fine and book is returned
    LOST;
}
