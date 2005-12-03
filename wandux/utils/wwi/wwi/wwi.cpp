// This is the main DLL file.

#include "stdafx.h"

#include "wwi.h"
#include <stdio.h>

#define _WIN32_DCOM
#include <iostream>

using namespace std;
#include <comdef.h>
#include <Wbemidl.h>


#pragma comment(lib, "wbemuuid.lib") 


JNIEXPORT jobjectArray JNICALL Java_pfe_migration_client_pre_service_WanduxWmiBridge_exec_1rq
(JNIEnv *env, jobject obj, jstring requete)
{
	HRESULT hres; 

	const char* msg=env->GetStringUTFChars(requete,0);
	//jclass cl = (*env)->GetObjectClass(env,obj);
	//jfieldID fid = (*env)->GetFieldID(env,cl,"Ljava/lang/String;" );
	//(*env)->SetStringField(env,obj,fid,requete) ;
	cout << msg << endl;

	bstr_t rq = bstr_t(msg);
	jobjectArray jobjectArray;
	jobject	jobject;
	// Step 1: -------------------------------------------------- 
	// Initialize COM. ------------------------------------------ 

	hres =  CoInitializeEx(0, COINIT_MULTITHREADED); 
	if (FAILED(hres)) 
	{ 
		cout << "Failed to initialize COM library. Error code = 0x" 
			<< hex << hres << endl; 
		//  return 1;                  // Program has failed. 
	} 

	// Step 2: -------------------------------------------------- 
	// Set general COM security levels -------------------------- 
	// Note: If you are using Windows 2000, you need to specify - 
	// the default authentication credentials for a user by using 
	// a SOLE_AUTHENTICATION_LIST structure in the pAuthList ---- 
	// parameter of CoInitializeSecurity ------------------------ 

	hres =  CoInitializeSecurity( 
		NULL, 
		-1,                          // COM authentication 
		NULL,                        // Authentication services 
		NULL,                        // Reserved 
		RPC_C_AUTHN_LEVEL_DEFAULT,   // Default authentication 
		RPC_C_IMP_LEVEL_IMPERSONATE, // Default Impersonation 
		NULL,                        // Authentication info 
		EOAC_NONE,                   // Additional capabilities 
		NULL                         // Reserved 
		); 

	if (FAILED(hres)) 
	{ 
		cout << "Failed to initialize security. Error code = 0x" 
			<< hex << hres << endl; 
		CoUninitialize(); 
		//      return 1;                    // Program has failed. 
	} 

	// Step 3: --------------------------------------------------- 
	// Obtain the initial locator to WMI ------------------------- 

	IWbemLocator *pLoc = NULL; 

	hres = CoCreateInstance( 
		CLSID_WbemLocator, 
		0, 
		CLSCTX_INPROC_SERVER, 
		IID_IWbemLocator, (LPVOID *) &pLoc); 

	if (FAILED(hres)) 
	{ 
		cout << "Failed to create IWbemLocator object." 
			<< " Err code = 0x" 
			<< hex << hres << endl; 
		CoUninitialize(); 
		//   return 1;                 // Program has failed. 
	} 


	// Step 4: ----------------------------------------------------- 
	// Connect to WMI through the IWbemLocator::ConnectServer method 

	IWbemServices *pSvc = NULL; 

	// Connect to the root\cimv2 namespace with 
	// the current user and obtain pointer pSvc 
	// to make IWbemServices calls. 
	hres = pLoc->ConnectServer( 
		_bstr_t(L"ROOT\\CIMV2"), // Object path of WMI namespace 
		NULL,                    // User name. NULL = current user 
		NULL,                    // User password. NULL = current 
		0,                       // Locale. NULL indicates current 
		NULL,                    // Security flags. 
		0,                       // Authority (e.g. Kerberos) 
		0,                       // Context object 
		&pSvc                    // pointer to IWbemServices proxy 
		); 

	if (FAILED(hres)) 
	{ 
		cout << "Could not connect. Error code = 0x" 
			<< hex << hres << endl; 
		pLoc->Release(); 
		CoUninitialize(); 
		//   return 1;                // Program has failed. 
	} 

	cout << "Connected to ROOT\\CIMV2 WMI namespace" << endl; 

	// Step 5: -------------------------------------------------- 
	// Set security levels on the proxy ------------------------- 


	hres = CoSetProxyBlanket( 
		pSvc,                        // Indicates the proxy to set 
		RPC_C_AUTHN_WINNT,           // RPC_C_AUTHN_xxx 
		RPC_C_AUTHZ_NONE,            // RPC_C_AUTHZ_xxx 
		NULL,                        // Server principal name 
		RPC_C_AUTHN_LEVEL_CALL,      // RPC_C_AUTHN_LEVEL_xxx 
		RPC_C_IMP_LEVEL_IMPERSONATE, // RPC_C_IMP_LEVEL_xxx 
		NULL,                        // client identity 
		EOAC_NONE                    // proxy capabilities 
		); 

	if (FAILED(hres)) 
	{ 
		cout << "Could not set proxy blanket. Error code = 0x" 
			<< hex << hres << endl; 
		pSvc->Release(); 
		pLoc->Release(); 
		CoUninitialize(); 
		//  return 1;               // Program has failed. 
	} 

	// Step 6: -------------------------------------------------- 
	// Use the IWbemServices pointer to make requests of WMI ---- 

	// For example, get the name of the operating system 
	IEnumWbemClassObject* pEnumerator = NULL; 
	hres = pSvc->ExecQuery( 
		bstr_t("WQL"), 
		bstr_t(rq), 
		WBEM_FLAG_FORWARD_ONLY | WBEM_FLAG_RETURN_IMMEDIATELY, 
		NULL, 
		&pEnumerator); 

	if (FAILED(hres)) 
	{ 
		cout << "Query for operating system name failed." 
			<< " Error code = 0x" 
			<< hex << hres << endl; 
		pSvc->Release(); 
		pLoc->Release(); 
		CoUninitialize(); 
		//   return 1;               // Program has failed. 
	} 



	// Step 7: ------------------------------------------------- 
	// Get the data from the query in step 6 ------------------- 

	IWbemClassObject *pclsObj; 
	ULONG uReturn = 0; 

	int size = 255;

//	IEnumWbemClassObject* pEnum = NULL;
//	pEnumerator->Clone(&pEnum);
//
//	while (pEnum) 
//	{ 
//		HRESULT hr = pEnum->Next(WBEM_INFINITE, 1, 
//			&pclsObj, &uReturn); 
//
//		
//cout << "pEnum 1" << endl;
//		if(0 == uReturn) 
//		{ 
//			
//cout << "pEnum 3" << endl;
//			break; 
//		} 
//
//cout << "pEnum 2" << endl;
//		size++;
//	}

	// pEnumerator->Reset();
	_jobjectArray* tab = env->NewObjectArray(size, env->FindClass("java/lang/String"), NULL);
	


	int i = 0;
	while(pEnumerator)
	{
		HRESULT hr = pEnumerator->Next(WBEM_INFINITE, 1, 
			&pclsObj, &uReturn); 

		if(0 == uReturn) 
		{ 
			break; 
		} 

		VARIANT vtProp; 
		VariantInit(&vtProp); 

		// Get the value of the Name property 
		hr = pclsObj->Get(L"Name", 0, &vtProp, 0, 0);

		//wcout << " OS Name : " << vtProp.bstrVal << endl; 
		
		int wideLen = 255; 
		char* asciStr = new char[wideLen+2];

		WideCharToMultiByte(
			CP_ACP,
			WC_COMPOSITECHECK,
			(LPCWSTR)vtProp.bstrVal,
			-1,
			asciStr,
			wideLen+1,
			NULL,
			NULL
			);
	
	//	cout << env->NewStringUTF(asciStr) << endl;
		env->SetObjectArrayElement(tab, i++, env->NewStringUTF(asciStr));
		VariantClear(&vtProp); 
	}

	// Cleanup 
	// ======== 

	pSvc->Release(); 
	pLoc->Release(); 
	pEnumerator->Release(); 
	//pEnum->Release(); 
	pclsObj->Release(); 
	CoUninitialize(); 

	// return 0;   // Program successfully completed. 

	return tab;
}



