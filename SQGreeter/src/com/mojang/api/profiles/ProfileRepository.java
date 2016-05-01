package com.mojang.api.profiles;

public abstract interface ProfileRepository
{
  public abstract Profile[] findProfilesByNames(String[] paramArrayOfString);
}

/* Location:           C:\Users\Drew\Desktop\SQDynamicWhitelist.jar
 * Qualified Name:     com.mojang.api.profiles.ProfileRepository
 * JD-Core Version:    0.6.2
 */