package com.poker;

import java.util.Dictionary;
import java.util.Hashtable;


public class Rule {

	//Dictionary <String, Object> mTestCondition;
	//private Dictionary<String, Object> mValueCondition;
	private Dictionary<String, Object> mCondition;
	final static String WILDCARD_ACES_VALUE = "Aces";
	final static String MAXINUM_ACES ="Max number of Aces";
	final static String TEST_WILDCARD = "Wild card test";
	final static String TEST_DEALER_CEILING = "Dealer ceiling test";
	
	public Rule() {
		// TODO Auto-generated constructor stub
		mCondition = new Hashtable <String, Object>();//new String(), new Integer() );
		init();
	}
	
	private void init() {
		mCondition.put(WILDCARD_ACES_VALUE, 11);
		mCondition.put(MAXINUM_ACES, 1);
		mCondition.put(TEST_WILDCARD, 21);
		mCondition.put(TEST_DEALER_CEILING, 17);	
	}
	
	public Object getCondition(String key)
	{
		return mCondition.get(key);
	}

}
