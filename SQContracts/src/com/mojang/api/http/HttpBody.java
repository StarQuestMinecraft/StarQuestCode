/*    */ package com.mojang.api.http;
/*    */ 
/*    */ public class HttpBody
/*    */ {
/*    */   private String bodyString;
/*    */ 
/*    */   public HttpBody(String bodyString)
/*    */   {
/*  8 */     this.bodyString = bodyString;
/*    */   }
/*    */ 
/*    */   public byte[] getBytes() {
/* 12 */     return this.bodyString != null ? this.bodyString.getBytes() : new byte[0];
/*    */   }
/*    */ }

/* Location:           C:\Users\Drew\Desktop\SQDynamicWhitelist.jar
 * Qualified Name:     com.mojang.api.http.HttpBody
 * JD-Core Version:    0.6.2
 */