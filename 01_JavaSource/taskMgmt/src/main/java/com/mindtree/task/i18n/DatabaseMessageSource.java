package com.mindtree.task.i18n;


import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import com.mindtree.task.model.I18nMessage;
import com.mindtree.task.model.Persistable;
import com.mindtree.task.service.I18nService;

public class DatabaseMessageSource extends AbstractMessageSource implements ResourceLoaderAware {

    private Logger log = Logger.getLogger(getClass());
    private ResourceLoader resourceLoader;

    private final Map<String, Map<String, String>> messages = new HashMap<>();  
    

    @Autowired
    private I18nService i18nService;

    public DatabaseMessageSource() {
        reload();
    }

    public DatabaseMessageSource(I18nService messageResourceService) {
        this.i18nService = messageResourceService;
        reload();
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String msg = getText(code, locale);
        return createMessageFormat(msg, locale);
    }

    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        return getText(code, locale);
    }
    
    public void reload() {
    	synchronized(this.messages){
    		messages.clear();
            messages.putAll(loadTexts());
    	}
    }
    
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = (resourceLoader != null ? resourceLoader : new DefaultResourceLoader());
    }    
    
    private String getText(String code, Locale locale) {
        Map<String, String> localized = messages.get(code);
        String textForCurrentLanguage = null;
        if (localized != null) {
        	
            textForCurrentLanguage = localized.get(locale.getLanguage());
            if (textForCurrentLanguage == null) {
                textForCurrentLanguage = localized.get(Locale.ENGLISH.getLanguage());
                //logger.debug("Fetched code:"+code +" locale: "+locale.getLanguage()+" message: "+textForCurrentLanguage);
            }
        }
        if (textForCurrentLanguage==null) {
            //Check parent message
            //logger.debug("Fallback to properties message:"+code);
            try {
                textForCurrentLanguage = getParentMessageSource().getMessage(code, null, locale);
            } catch (Exception e) {
                logger.error("Cannot find message with code: " + code);
            }
        }
        return textForCurrentLanguage != null ? textForCurrentLanguage : code;
    }

    protected Map<String, Map<String, String>> loadTexts() {
        log.info("Loading All i18n messages from table...");
        Map<String, Map<String, String>> m = null;
       
        List<Persistable> i18MsgLst = i18nService.getProperties();
      
        for (Persistable temp : i18MsgLst) {
        	I18nMessage i18nMsg =  (I18nMessage)temp;
        	//logger.debug(i18nMsg.getI18nMsgID().getCode()+" "+i18nMsg.getI18nMsgID().getLocale().getLocale()+" "+i18nMsg.getMessage());
            
            if (m == null)
				m = new HashMap<>();
	            Map<String, String> data = m.get(i18nMsg.getI18nMsgID().getCode());
				if (data == null) {
					data = new HashMap<>();
					m.put(i18nMsg.getI18nMsgID().getCode(), data);
				}
	
				data.put(i18nMsg.getI18nMsgID().getLocale().getLocale(), i18nMsg.getMessage()); 

			m.put(i18nMsg.getI18nMsgID().getCode(), data);
        }
        return m;
    }
    
    
    
    
}