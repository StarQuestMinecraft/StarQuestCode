/*    */ package com.mojang.api.http;
/*    */ 
/*    */ public class HttpHeader
/*    */ {
/*    */   private String name;
/*    */   private String value;
/*    */ 
/*    */   public HttpHeader(String name, String value)
/*    */   {
/*  8 */     this.name = name;
/*  9 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 13 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 17 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public String getValue() {
/* 21 */     return this.value;
/*    */   }
/*    */ 
/*    */   public void setValue(String value) {
/* 25 */     this.value = value;
/*    */   }
/*    */ }

/* Location:           C:\Users\Drew\Desktop\SQDynamicWhitelist.jar
 * Qualified Name:     com.mojang.api.http.HttpHeader
 * JD-Core Version:    0.6.2
 */