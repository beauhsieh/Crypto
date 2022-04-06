package com.crypto.crypto.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class FormDataXssRequest extends HttpServletRequestWrapper {
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request
     * @throws IllegalArgumentException if the request is null
     */
    public FormDataXssRequest(HttpServletRequest request) {
        super(request);
    }
    //替換非法字符
    private String clean(String s){
        System.out.println("do xss");
        s=s.replaceAll("<","&lt;").replaceAll("script","").replaceAll("eval\\((.*)\\)","");
        return s;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if(values==null){
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for(int i= 0;i<count;i++){
            encodedValues[i] = clean(values[i]);

        }
        return encodedValues;
    }

}