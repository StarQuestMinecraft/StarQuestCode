package com.mojang.api.http;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import java.util.List;

public abstract interface HttpClient
{
  public abstract String post(URL paramURL, HttpBody paramHttpBody, List<HttpHeader> paramList)
    throws IOException;

  public abstract String post(URL paramURL, Proxy paramProxy, HttpBody paramHttpBody, List<HttpHeader> paramList)
    throws IOException;
}

/* Location:           C:\Users\Drew\Desktop\SQDynamicWhitelist.jar
 * Qualified Name:     com.mojang.api.http.HttpClient
 * JD-Core Version:    0.6.2
 */