package com.minecraftdimensions.bungeesuitechat.objects;


public class Channel {
	private String name;
	private String format;
	private String owner;
	private boolean muted;
	public boolean isDefault;
	public boolean open;
	
	public Channel(String name, String format, String owner, boolean muted, boolean isDefault, boolean open){
		this.name = name;
		this.format = format;
		this.owner = owner;
		this.muted = muted;
		this.isDefault = isDefault;
		this.open=open;
	}
	
	public Channel(String serialised){
		String data[] = serialised.split("~");
		name = data[0];
		format = data[1];
		owner = data[2];
		muted = Boolean.parseBoolean(data[3]);
		isDefault = Boolean.parseBoolean(data[4]);
		open = Boolean.parseBoolean(data[5]);
	}
	
	
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getFormat(){
		return format;
	}
	public void setFormat(String format){
		this.format=format;
	}
	public String getOwner(){
		return owner;
	}
	public void setOwner(String owner){
		this.owner = owner;
	}
	public boolean isOwner(String owner){
		return this.owner.equals(owner);
	}
	public boolean isMuted(){
		return muted;
	}
	public void setMuted(boolean mute){
		this.muted = mute;
	}
	public boolean isDefault(){
		return isDefault;
	}
	public String serialise(){
		return name+"~"+format+"~"+owner+"~"+muted+"~"+isDefault+"~"+open;
	}

}
