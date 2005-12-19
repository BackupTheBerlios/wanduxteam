// wwi.h

#pragma once


namespace wwi {

	public ref class Class1
	{
		// TODO: Add your methods for this class here.
	};
}



/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class pfe_migration_client_pre_service_WanduxWmiBridge */

#ifndef _Included_pfe_migration_client_pre_service_WanduxWmiBridge
#define _Included_pfe_migration_client_pre_service_WanduxWmiBridge
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     pfe_migration_client_pre_service_WanduxWmiBridge
 * Method:    connexion
 * Signature: (Ljava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_pfe_migration_client_pre_service_WanduxWmiBridge_connexion
  (JNIEnv *, jobject, jstring);

/*
 * Class:     pfe_migration_client_pre_service_WanduxWmiBridge
 * Method:    deconnexion
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_pfe_migration_client_pre_service_WanduxWmiBridge_deconnexion
  (JNIEnv *, jobject);

/*
 * Class:     pfe_migration_client_pre_service_WanduxWmiBridge
 * Method:    exec_rq
 * Signature: (Ljava/lang/String;Ljava/lang/String;)Lcom/jacob/com/Variant;
 */
JNIEXPORT jobject JNICALL Java_pfe_migration_client_pre_service_WanduxWmiBridge_exec_1rq
  (JNIEnv *, jobject, jstring, jstring);

#ifdef __cplusplus
}
#endif
#endif






/////* DO NOT EDIT THIS FILE - it is machine generated */
//#include <jni.h>
///* Header for class pfe_migration_client_pre_service_WanduxWmiBridge */
//
//#ifndef _Included_pfe_migration_client_pre_service_WanduxWmiBridge
//#define _Included_pfe_migration_client_pre_service_WanduxWmiBridge
//#ifdef __cplusplus
//extern "C" {
//#endif
///*
// * Class:     pfe_migration_client_pre_service_WanduxWmiBridge
// * Method:    connexion
// * Signature: (Ljava/lang/String;)Ljava/lang/Boolean;
// */
//JNIEXPORT jobject JNICALL Java_pfe_migration_client_pre_service_WanduxWmiBridge_connexion
//  (JNIEnv *, jobject, jstring);
//
///*
// * Class:     pfe_migration_client_pre_service_WanduxWmiBridge
// * Method:    exec_rq
// * Signature: (Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;
// */
//JNIEXPORT jobjectArray JNICALL Java_pfe_migration_client_pre_service_WanduxWmiBridge_exec_1rq
//  (JNIEnv *, jobject, jstring, jstring);
//
//#ifdef __cplusplus
//}
//#endif
//#endif

//struct _wireVARIANT {
//    DWORD clSize; /* wire buffer length in units of hyper (int64)
//*/
//    DWORD rpcReserved; /* for future use */
//    USHORT vt;
//    USHORT wReserved1;
//    USHORT wReserved2;
//    USHORT wReserved3;
//    [switch_type(ULONG), switch_is(vt)] union {
//    [case(VT_I4)] LONG lVal; /* VT_I4
//*/
//    [case(VT_UI1)] BYTE bVal; /* VT_UI1
//*/
//    [case(VT_I2)] SHORT iVal; /* VT_I2
//*/
//    [case(VT_R4)] FLOAT fltVal; /* VT_R4
//*/
//    [case(VT_R8)] DOUBLE dblVal; /* VT_R8
//*/
//    [case(VT_BOOL)] VARIANT_BOOL boolVal; /* VT_BOOL
//*/
//    [case(VT_ERROR)] SCODE scode; /* VT_ERROR
//*/
//    [case(VT_CY)] CY cyVal; /* VT_CY
//*/
//    [case(VT_DATE)] DATE date; /* VT_DATE
//*/
//    [case(VT_BSTR)] wireBSTR bstrVal; /* VT_BSTR
//*/
//    [case(VT_UNKNOWN)] IUnknown * punkVal; /* VT_UNKNOWN
//*/
//    [case(VT_DISPATCH)] IDispatch * pdispVal; /* VT_DISPATCH
//*/
//    [case(VT_ARRAY)] wireSAFEARRAY parray; /* VT_ARRAY
//*/
//
//    [case(VT_RECORD, VT_RECORD|VT_BYREF)]
//                        wireBRECORD brecVal; /* VT_RECORD
//*/
//
//    [case(VT_UI1|VT_BYREF)]
//                        BYTE * pbVal; /* VT_BYREF|VT_UI1
//*/
//    [case(VT_I2|VT_BYREF)]
//                        SHORT * piVal; /* VT_BYREF|VT_I2
//*/
//    [case(VT_I4|VT_BYREF)]
//                        LONG * plVal; /* VT_BYREF|VT_I4
//*/
//    [case(VT_R4|VT_BYREF)]
//                        FLOAT * pfltVal; /* VT_BYREF|VT_R4
//*/
//    [case(VT_R8|VT_BYREF)]
//                        DOUBLE * pdblVal; /* VT_BYREF|VT_R8
//*/
//    [case(VT_BOOL|VT_BYREF)]
//                        VARIANT_BOOL *pboolVal; /* VT_BYREF|VT_BOOL
//*/
//    [case(VT_ERROR|VT_BYREF)]
//                        SCODE * pscode; /* VT_BYREF|VT_ERROR
//*/
//    [case(VT_CY|VT_BYREF)]
//                        CY * pcyVal; /* VT_BYREF|VT_CY
//*/
//    [case(VT_DATE|VT_BYREF)]
//                        DATE * pdate; /* VT_BYREF|VT_DATE
//*/
//    [case(VT_BSTR|VT_BYREF)]
//                        wireBSTR * pbstrVal; /* VT_BYREF|VT_BSTR
//*/
//    [case(VT_UNKNOWN|VT_BYREF)]
//                        IUnknown ** ppunkVal; /* VT_BYREF|VT_UNKNOWN
//*/
//    [case(VT_DISPATCH|VT_BYREF)]
//                        IDispatch ** ppdispVal; /* VT_BYREF|VT_DISPATCH
//*/
//    [case(VT_ARRAY|VT_BYREF)]
//                        wireSAFEARRAY *pparray; /* VT_BYREF|VT_ARRAY
//*/
//    [case(VT_VARIANT|VT_BYREF)]
//                        wireVARIANT * pvarVal; /* VT_BYREF|VT_VARIANT
//*/
//
//    [case(VT_I1)] CHAR cVal; /* VT_I1
//*/
//    [case(VT_UI2)] USHORT uiVal; /* VT_UI2
//*/
//    [case(VT_UI4)] ULONG ulVal; /* VT_UI4
//*/
//    [case(VT_INT)] INT intVal; /* VT_INT
//*/
//    [case(VT_UINT)] UINT uintVal; /* VT_UINT
//*/
//    [case(VT_DECIMAL)] DECIMAL decVal; /* VT_DECIMAL
//*/
//
//    [case(VT_BYREF|VT_DECIMAL)]
//                        DECIMAL * pdecVal; /* VT_BYREF|VT_DECIMAL
//*/
//    [case(VT_BYREF|VT_I1)]
//                        CHAR * pcVal; /* VT_BYREF|VT_I1
//*/
//    [case(VT_BYREF|VT_UI2)]
//                        USHORT * puiVal; /* VT_BYREF|VT_UI2
//*/
//    [case(VT_BYREF|VT_UI4)]
//                        ULONG * pulVal; /* VT_BYREF|VT_UI4
//*/
//    [case(VT_BYREF|VT_INT)]
//                        INT * pintVal; /* VT_BYREF|VT_INT
//*/
//    [case(VT_BYREF|VT_UINT)]
//                        UINT * puintVal; /* VT_BYREF|VT_UINT
//*/
//    [case(VT_EMPTY)] ; /* nothing
//*/
//    [case(VT_NULL)] ; /* nothing
//*/
//    };
//};
//
