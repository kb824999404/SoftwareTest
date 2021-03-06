package com.tongji.softwaretest.Tool;

import com.tongji.softwaretest.Config.FileConfig;
import com.tongji.softwaretest.Entity.TestResult;


import javax.tools.*;
import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class TestServices {
    private static Object convert_value(String str, Class type) {
        if (type == int.class || type == Integer.class) {
            return Integer.valueOf(str);
        }
        if (type == long.class || type == Long.class) {
            return Long.valueOf(str);
        }
        if (type == float.class || type == Float.class) {
            return Float.valueOf(str);
        }
        if (type == double.class || type == Double.class) {
            return Double.valueOf(str);
        }
        return str;
    }
    private static boolean compareResult(Object v1,Object v2,Class type){
        if (type == float.class || type == Float.class || type == double.class || type == Double.class) {
            return Math.abs((Double)(v1)-(Double)(v2))<1e-6;
        }else{
            return v1.equals(v2);
        }

    }
    public static Map<String, List<String>> getMethods(String class_name) {
        List<String> result = new ArrayList<>();
        List<String> origin = new ArrayList<>();
        try {
            Class clazz = Class.forName(class_name);
            var methods = clazz.getMethods();
            for (var item : methods) {
                if (item.getDeclaringClass() == clazz) {
                    var name = new StringBuffer();
                    if (Modifier.isStatic(item.getModifiers())) {
                        name.append("static ");
                    }
                    name.append(item.getReturnType().getSimpleName());
                    name.append(" ");
                    name.append(item.getName());
                    name.append("(");
                    var paras = item.getParameterTypes();
                    for (int i = 0; i < paras.length; ++i) {
                        if (i != 0) {
                            name.append(", ");
                        }
                        name.append(paras[i].getSimpleName());
                    }
                    name.append(")");
                    result.add(name.toString());
                    origin.add(item.getName());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Map<String, List<String>> map = new HashMap<>();
        map.put("simple_name", origin);
        map.put("name", result);
        return map;

    }

    public static List<TestResult> singleTest(String class_name,
        String method_name, String params) {
        List<TestResult> results = new ArrayList<>();

        try {

            // 获取待测类
            Class clazz = Class.forName(class_name);
            // 获取待测方法
            var methods = clazz.getMethods();

            Method method = null;
            for (var item : methods) {

                if (item.getName().equals(method_name)) {
                    method = item;
                }
            }

                String[] cols = params.split("\\s+");
                var para_num = method.getParameterCount();
                List<Object> parameters = new ArrayList<>();
                
                // 构造待测方法使用的参数列表
                for (int i = 0; i < para_num; ++i) {
                    parameters.add(convert_value(cols[i], method.getParameterTypes()[i]));
                }
                
                Object return_v;
                var true_result=method.getReturnType().getConstructor(String.class).newInstance(cols[cols.length-1]);
                try{
                    // 判断待测方法是否是static方法，从而以不同形式调用该方法
                    if (Modifier.isStatic(method.getModifiers())) {
                        return_v = method.invoke(null, parameters.toArray());
                    } else {
                        return_v = method.invoke(clazz.getDeclaredConstructor().newInstance(),parameters.toArray());
                    }
                }catch (Exception e){
                    System.out.println(e.getMessage());
                    return_v=null;
                }
                
                
                // 比对模块输出结果与预计结果的差异
                // 并设置测试结果信息
                TestResult testResult = new TestResult();
                testResult.setParameters(parameters);
                testResult.setResult(compareResult(return_v,true_result,method.getReturnType()));
                testResult.setReal_result(return_v.toString());
                testResult.setRight_result(true_result.toString());
                testResult.setClass_name(clazz.getSimpleName());
                testResult.setMethod_name(method_name);
                results.add(testResult);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            Runtime.getRuntime().gc();
        }
        return results;
    }


    public static List<TestResult> testClass(String class_name, String test_case_path, String method_name) {
        List<TestResult> results = new ArrayList<>();

        try {
            File test_case_file = new File(test_case_path);
          

            // 获取待测类
            Class clazz = Class.forName(class_name);
            // 获取待测方法
            var methods = clazz.getMethods();

            Method method = null;
            for (var item : methods) {

                if (item.getName().equals(method_name)) {
                    method = item;
                }
            }


            BufferedReader reader = null;
            try {
                // 从输入测试用例文件中获取待测模块输入
                InputStreamReader isr = new InputStreamReader(new FileInputStream(test_case_file), "UTF-8");
                reader = new BufferedReader(isr);
                String line = null;
                while ((line = reader.readLine()) != null) {
                    String[] cols = line.split("\\s+");
                    var para_num = method.getParameterCount();
                    List<Object> parameters = new ArrayList<>();
                    
                    // 构造待测方法使用的参数列表
                    for (int i = 0; i < para_num; ++i) {
                        parameters.add(convert_value(cols[i], method.getParameterTypes()[i]));
                    }
                    
                    Object return_v;
                    var true_result=method.getReturnType().getConstructor(String.class).newInstance(cols[cols.length-1]);
                    try{
                        // 判断待测方法是否是static方法，从而以不同形式调用该方法
                        if (Modifier.isStatic(method.getModifiers())) {
                            return_v = method.invoke(null, parameters.toArray());
                        } else {
                            return_v = method.invoke(clazz.getDeclaredConstructor().newInstance(),parameters.toArray());
                        }
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                        return_v=null;
                    }
                    
                    
                    // 比对模块输出结果与预计结果的差异
                    // 并设置测试结果信息
                    TestResult testResult = new TestResult();
                    testResult.setParameters(parameters);
                    testResult.setResult(compareResult(return_v,true_result,method.getReturnType()));
                    testResult.setReal_result(return_v.toString());
                    testResult.setRight_result(true_result.toString());
                    testResult.setClass_name(clazz.getSimpleName());
                    testResult.setMethod_name(method_name);
                    results.add(testResult);

                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e1) {
                    }
                }
            }   

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            Runtime.getRuntime().gc();
        }
        return results;
    }

    static public Boolean addClass(String path, String class_name) {
        if (System.getProperty("os.name").toLowerCase().startsWith("wind")) {
            path = path.replaceFirst("/", "");
        }

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        Iterable<String> options = Arrays.asList("-d", path + "../../","-encoding","utf-8");
        StandardJavaFileManager fileManager = compiler
                .getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> compilationUnits = fileManager
                .getJavaFileObjectsFromFiles(Arrays.asList(new File(
                        path + class_name + ".java")));

        return compiler.getTask(null, fileManager, null, options,
                null, compilationUnits).call();
    }
}