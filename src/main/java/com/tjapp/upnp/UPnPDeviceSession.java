package com.tjapp.upnp;

import java.net.*;


class UPnPDeviceSession {

	private UPnPDeviceSessionStatus status = UPnPDeviceSessionStatus.PENDING;
	private long registerTick;
	private long timeout;
	private UPnPDevice device;
	private String baseUrl;

	public UPnPDeviceSession () {
		registerTick = Clock.getTickMilli();
		timeout = 30 * 1000;	// default 30 seconds
	}

	public String getUdn() {
		return device.getUdn();
	}

	public String getDeviceType() {
		return device.getDeviceType();
	}

	public UPnPActionResponse invokeAction(UPnPActionRequest request) throws Exception {
		return UPnPActionInvoke.invoke(new URL(baseUrl), request);
	}

	public UPnPDevice getDevice() {
		return device;
	}

	public void setDevice(UPnPDevice device) {
		this.device = device;
	}

	public UPnPService getService(String serviceType) {
		return device.getService(serviceType);
	}

	public boolean expired() {
		long dur = Clock.getTickMilli() - registerTick;
		return dur >= timeout;
	}

	public UPnPDeviceSessionStatus getStatus() {
		return status;
	}

	public void setStatus(UPnPDeviceSessionStatus status) {
		this.status = status;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public void setRegisterTick(long registerTick) {
		this.registerTick = registerTick;
	}

	public long getRegisterTick() {
		return registerTick;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public long getTimeout() {
		return timeout;
	}
}
