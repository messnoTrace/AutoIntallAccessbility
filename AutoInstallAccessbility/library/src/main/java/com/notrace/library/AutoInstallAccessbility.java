package com.notrace.library;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * Created by notrace on 2016/9/1.
 */
public class AutoInstallAccessbility extends AccessibilityService {
    private String[] keys = new String[]{"激活", "下一步", "好", "安装", "仅允许一次", "安装新版本", "僅允許一次", "開啟", "替换", "替換", "允许一次", "允許一次", "繼續", "確定", "继续", "继续安装", "允许本次安装", "同意并继续", "确定", "仍然允许", "确认", "始终", "解除禁止", "允许", "启动", "立即清理", "完成", "关闭", "清理", "解除", "等待", "继续卸载", "立即清除", "卸载", "立即删除", "ACTIVATE", "Activate"};

    private String[]install=new String[]{"安装"};
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int type=event.getEventType();

        Log.d("==============event",type+"");

//        checkInstall(event);
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if(nodeInfo!=null)
        {
            CharSequence packageName=event.getPackageName();
            //安装，sec是三星权限界面
            if(packageName.toString().contains("packageinstaller")||packageName.toString().contains("sec"))
            {
                //包名中有installer之类的安装界面
                for(int i=0;i<keys.length;i++) {
                    //TODO 通过找界面上的控件包含关键字的，为啥不好使哩？
                    String key=keys[i];
                    List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText(key);
                    if (list != null && list.size() > 0) {
                        for (int j = 0; j < list.size(); j++) {
                            {
                                if (list.get(j).getClassName().equals("android.widget.Button")&&list.get(j).isEnabled()) {
                                    list.get(j).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("=====", "AutoInstallAccessbilityCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("=====", "AutoInstallAccessbilityDestroy");
    }
}
