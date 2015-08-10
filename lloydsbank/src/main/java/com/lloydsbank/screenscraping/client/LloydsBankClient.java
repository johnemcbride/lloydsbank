package com.lloydsbank.screenscraping.client;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;

import com.gargoylesoftware.htmlunit.Cache;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlHeader;
import com.gargoylesoftware.htmlunit.html.HtmlHeading2;
import com.gargoylesoftware.htmlunit.html.HtmlHeading3;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlLabel;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlParagraph;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlOption;


public class LloydsBankClient{
	private String username; 
	private String password;
	private String memorableWord;
	
    public LloydsBankClient(String username, String password, String memorableWord){
    	this.username=username; 
     	this.password=password;
     	this.memorableWord = memorableWord;
    }
    
	
	public BigDecimal fetchBalance() {

		WebClient browser = new WebClient();
        browser.getOptions().setJavaScriptEnabled(false);
        browser.getOptions().setCssEnabled(false);
		HtmlPage currentPage = null;
		BigDecimal balance = new BigDecimal(0);
		//NumberFormat nf = NumberFormat.getNumberInstance(Locale.UK);
		DecimalFormat nf = (DecimalFormat)NumberFormat.getInstance(Locale.UK);
		nf.setParseBigDecimal(true);
	        
		//nf.setCurrency(Currency.getInstance(Locale.ENGLISH));
		
		try {
			currentPage = browser.getPage("https://online.lloydsbank.co.uk/personal/logon/login.jsp?WT.ac=PLO0512");
			((HtmlTextInput)currentPage.getElementByName("frmLogin:strCustomerLogin_userID")).setValueAttribute(username);
			((HtmlPasswordInput)currentPage.getElementByName("frmLogin:strCustomerLogin_pwd")).setValueAttribute(password);
			currentPage = ((HtmlInput)currentPage.getElementById("frmLogin:btnLogin1")).click();
			//loginpage clicked
		
			
			String First = ((HtmlLabel)currentPage.getFirstByXPath("//html/body/div/div[2]/div/div[1]/div/div/form/fieldset/div/div/div[1]/label")).asText();
			String Second = ((HtmlLabel)currentPage.getFirstByXPath("//html/body/div/div[2]/div/div[1]/div/div/form/fieldset/div/div/div[2]/label")).asText();
			String Third = ((HtmlLabel)currentPage.getFirstByXPath("//html/body/div/div[2]/div/div[1]/div/div/form/fieldset/div/div/div[3]/label")).asText();

			String firstChar = "&nbsp;"+String.valueOf(memorableWord.charAt(Integer.parseInt(String.valueOf(First.charAt(10)))-1));
			String secondChar = "&nbsp;"+String.valueOf(memorableWord.charAt(Integer.parseInt(String.valueOf(Second.charAt(10)))-1));
			String thirdChar = "&nbsp;"+String.valueOf(memorableWord.charAt(Integer.parseInt(String.valueOf(Third.charAt(10)))-1));
			
			
/*
			(((HtmlSelect)currentPage.getElementByName("frmLogin:strEnterMI1")).getOptionByValue(firstChar)).setSelected(true);
			(((HtmlSelect)currentPage.getElementByName("frmLogin:strEnterMI2")).getOptionByValue(secondChar)).setSelected(true);
			(((HtmlSelect)currentPage.getElementByName("frmLogin:strEnterMI3")).getOptionByValue(thirdChar)).setSelected(true); 
			
			*/
			(((HtmlSelect)currentPage.getElementByName("frmentermemorableinformation1:strEnterMemorableInformation_memInfo1")).getOptionByValue(firstChar)).setSelected(true);
			(((HtmlSelect)currentPage.getElementByName("frmentermemorableinformation1:strEnterMemorableInformation_memInfo2")).getOptionByValue(secondChar)).setSelected(true);
			(((HtmlSelect)currentPage.getElementByName("frmentermemorableinformation1:strEnterMemorableInformation_memInfo3")).getOptionByValue(thirdChar)).setSelected(true); 
			
			
			

			//currentPage = ((HtmlInput)currentPage.getElementById("frmLogin:btnSubmit")).click();
			currentPage = ((HtmlInput)currentPage.getElementById("frmentermemorableinformation1:btnContinue")).click();
			String balanceStr = ((HtmlParagraph)currentPage.getFirstByXPath("//html/body/div[2]/div[2]/div/div[1]/div/div/ul/li/div[1]/div[2]/div[1]/p[1]")).asText();
			String balanceNum = balanceStr.replaceAll("Balance £","");
			
			try {
				balance = (BigDecimal)(nf.parseObject(balanceNum.replaceAll(",", "")));
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			browser.closeAllWindows();
			browser.setCache(new Cache());
			browser.setCookieManager(new CookieManager());
			
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return balance;
	
	}
}
