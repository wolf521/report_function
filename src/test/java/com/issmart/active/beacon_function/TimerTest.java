package com.issmart.active.beacon_function;

import org.junit.Test;

import com.issmart.common.serviceImpl.TimerServiceImpl;

public class TimerTest {

	@Test
	public void TimerServiceTest() {
		 new TimerServiceImpl().opeMemberActiveTimer();
	}
	
	public static void main(String[] args) {
		new TimerServiceImpl().opeBoothActiveTimer();
	}
	
	@Test
	public void BoothTimerServiceTest() {
		 new TimerServiceImpl().opeBoothActiveTimer();
	}
}
