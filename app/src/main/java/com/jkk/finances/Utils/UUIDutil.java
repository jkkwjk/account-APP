package com.jkk.finances.Utils;

import java.util.UUID;

public class UUIDutil {
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-","");
	}
}
