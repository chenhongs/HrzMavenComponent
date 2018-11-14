package com.ch.android.appjoint

import com.android.build.api.transform.Format;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInvocation;
import com.google.common.collect.Sets
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

/**
 * Created by mac on 2018/11/14.
 */

 class PluginTransform extends Transform {
    @Override
     String getName() {
        return "appJointPlugin";
    }

    @Override
     Set<QualifiedContent.ContentType> getInputTypes() {
        ////返回单元素Set集合
        //输入类型 为字节码集合
        return Collections.singleton(QualifiedContent.DefaultContentType.CLASSES);
    }

    @Override
     Set<? super QualifiedContent.Scope> getScopes() {
        //Scope限定范围  immutableEnumSet不可变的集合
        return Sets.immutableEnumSet(
                QualifiedContent.Scope.PROJECT,
                QualifiedContent.Scope.SUB_PROJECTS,
                QualifiedContent.Scope.EXTERNAL_LIBRARIES
        );
    }

    /**
     * 是否增量
     * @return
     */
    @Override
     boolean isIncremental() {
        return true;
    }

    @Override
     void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
       //开始插桩

        //异构的对象引用列表
        def clzzs=[]

        transformInvocation.inputs.each { inputs ->

            inputs.jarInputs.each { jarinputs ->
                jarinputs
            }

        }





    }
}
