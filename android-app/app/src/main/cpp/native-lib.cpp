#include <jni.h>
#include <opencv2/opencv.hpp>

extern "C"
JNIEXPORT jbyteArray JNICALL
Java_com_example_camopencv_NativeBridge_processFrame(JNIEnv* env, jobject, jbyteArray input, jint w, jint h){
    jbyte* bytes = env->GetByteArrayElements(input,nullptr);
    cv::Mat rgba(h,w,CV_8UC4,(unsigned char*)bytes);
    cv::Mat gray,out;
    cv::cvtColor(rgba,gray,cv::COLOR_RGBA2GRAY);
    cv::cvtColor(gray,out,cv::COLOR_GRAY2RGBA);
    int size = out.total()*out.elemSize();
    jbyteArray result = env->NewByteArray(size);
    env->SetByteArrayRegion(result,0,size,(jbyte*)out.data);
    env->ReleaseByteArrayElements(input,bytes,JNI_ABORT);
    return result;
}
