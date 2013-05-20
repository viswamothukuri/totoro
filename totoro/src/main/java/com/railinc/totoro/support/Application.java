package com.railinc.totoro.support;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Application {
        private static final String BUNDLE_NAME = "totoro"; //$NON-NLS-1$

        private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

        
        public static String getVersion(){
                String x = getString("application.version");
                if (isNotBlank(System.getProperty("wtp.deploy"))) {
                        try { 
                                x = x.concat("-").concat(InetAddress.getLocalHost().getHostName());
                        } catch (UnknownHostException e) {
                                e.printStackTrace();
                        }
                }
                return x;
        }
        
        public static String getString(String key) {
                try {
                        return RESOURCE_BUNDLE.getString(key);
                } catch (MissingResourceException e) {
                        return '!' + key + '!';
                }
        }

}
