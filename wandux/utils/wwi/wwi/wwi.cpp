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



/*
* declaration en static globale pour gerer les aller-retour de requette entre java et la dll
*/
static IWbemServices *pSvc = NULL; 
static	IWbemLocator *pLoc = NULL; 


char * bstrVal_to_string(BSTR bstrVal)
{
	int wideLen = 255; 
				char* asciStr = new char[wideLen+2];
				WideCharToMultiByte(
					CP_ACP,
					WC_COMPOSITECHECK,
					(LPCWSTR)bstrVal,
					-1,
					asciStr,
					wideLen+1,
					NULL,
					NULL
					);
	return asciStr;
}
char* f_test_type_of_variant(VARIANT vtProp)
{

	//http://dev.eclipse.org/viewcvs/index.cgi/org.eclipse.swt.snippets/src/org/eclipse/swt/snippets/Snippet81.java?rev=HEAD

	char * result = NULL;


	//cout << V_VT(&vtProp) << endl;
	switch(V_VT(&vtProp))
	{
		case VT_BSTR: 
			{
				
				//_bstr_t bstrPath = &vtProp; //Just to convert BSTR to ANSI
				// char* result=(char*)bstrPath;   
				result = bstrVal_to_string(vtProp.bstrVal);
				break;
			}
		case VT_SAFEARRAY:
		{
			
			break;
		}
		case VT_ARRAY:
		{
			cout << "VT_ARRAY " << endl;
			break;
		}

		case VT_CARRAY:
		{
			cout << "VT_CARRAY " << endl;
			break;
		}
		case VT_BOOL:
		{
			(vtProp.boolVal == 0 ) ? result = "false" : result = "true";
			break;
		}
		case VT_INT:
		{
			break;
		}
		case VT_DATE:
		{
			break;
		}
		case VT_UINT:	
		{
			break;
		}
		
		case VT_UI8:
		{
			break;
		}
		case VT_BLOB:
		{
			cout << "VT_BLOB " << endl;
			break;
		}
		case 8200:	
		//case (VT_BYREF|*) :
		{
			cout << "8200 " << endl;
			result =  bstrVal_to_string(vtProp.bstrVal);
			break;
		}
	}
	return result;
}






/*
* cree une connexion sur WMI, et stock le handel en global pour une utilisation  multiple entre java et cpp
*/
JNIEXPORT jboolean JNICALL Java_pfe_migration_client_pre_service_WanduxWmiBridge_connexion
  (JNIEnv *env, jobject, jstring rootPath)
{
		HRESULT hres; 
	const char* rootPATH=env->GetStringUTFChars(rootPath,0);
	bstr_t ROOT_PATH = bstr_t(rootPATH);
		env->ReleaseStringUTFChars(rootPath, rootPATH);

// Step 1: -------------------------------------------------- 
	// Initialize COM. ------------------------------------------ 

	hres =  CoInitializeEx(0, COINIT_MULTITHREADED); 
	if (FAILED(hres)) 
	{ 
		//cout << "Failed to initialize COM library. Error code = 0x" 
		//	<< hex << hres << endl; 
		//char *errMsg = "Failed to initialize COM library. Error code = 0x" ;
		//env->SetObjectArrayElement(tab, 0, env->NewStringUTF(errStr));
		//env->SetObjectArrayElement(tab, 1, env->NewStringUTF(errMsg));
		//return tab;
		return (jboolean) false;
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
		//cout << "Failed to initialize security. Error code = 0x" 
		//	<< hex << hres << endl; 
		//char *errMsg =  "Failed to initialize security. Error code = 0x" ;
		//CoUninitialize(); 
		//
		//env->SetObjectArrayElement(tab, 0, env->NewStringUTF(errStr));
		//env->SetObjectArrayElement(tab, 1, env->NewStringUTF(errMsg));
		//return tab;
		return (jboolean) false;
	} 

	// Step 3: --------------------------------------------------- 
	// Obtain the initial locator to WMI ------------------------- 

//	IWbemLocator *pLoc = NULL; 

	hres = CoCreateInstance( 
		CLSID_WbemLocator, 
		0, 
		CLSCTX_INPROC_SERVER, 
		IID_IWbemLocator, (LPVOID *) &pLoc); 

	if (FAILED(hres)) 
	{ 
		//cout << "Failed to create IWbemLocator object." 
		//	<< " Err code = 0x" 
		//	<< hex << hres << endl; 
		//char *errMsg =   "Failed to create IWbemLocator object."; 
		//CoUninitialize(); 

		//env->SetObjectArrayElement(tab, 0, env->NewStringUTF(errStr));
		//env->SetObjectArrayElement(tab, 1, env->NewStringUTF(errMsg));
		//return tab;
		return (jboolean) false;
	} 


	// Step 4: ----------------------------------------------------- 
	// Connect to WMI through the IWbemLocator::ConnectServer method 

//	IWbemServices *pSvc = NULL; 

	// Connect to the root\cimv2 namespace with 
	// the current user and obtain pointer pSvc 
	// to make IWbemServices calls. 
	hres = pLoc->ConnectServer( 
		ROOT_PATH, // Object path of WMI namespace 
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
		//cout << "Could not connect. Error code = 0x" 
		//	<< hex << hres << endl; 
		//char *errMsg =   "Could not connect. Error code = 0x"; 
		//pLoc->Release(); 
		//CoUninitialize();
		//env->SetObjectArrayElement(tab, 0, env->NewStringUTF(errStr));
		//env->SetObjectArrayElement(tab, 1, env->NewStringUTF(errMsg));
		//return tab;
		return (jboolean) false;
	} 

	wcout << "Connected to " << ROOT_PATH << endl; 

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
		char *errMsg =   "Could not set proxy blanket. Error code = AFAIRE"; 
		pSvc->Release(); 
		pLoc->Release(); 
		CoUninitialize(); 
		
		//env->SetObjectArrayElement(tab, 0, env->NewStringUTF(errStr));
		//env->SetObjectArrayElement(tab, 1, env->NewStringUTF(errMsg));
		//return tab;
		return (jboolean) false;
	} 


		return (jboolean) true;
}







/*
* methode exec_rq: permet d'executer une requette WMI sur la connexion deja ouverte.
*/

JNIEXPORT jobjectArray JNICALL Java_pfe_migration_client_pre_service_WanduxWmiBridge_exec_1rq
(JNIEnv *env, jobject obj, jstring requete, jstring wzName)
{
	HRESULT hres; 
	// recuperation et preparation des parametres
	const char* msg=env->GetStringUTFChars(requete,0);
	const char* wZName=env->GetStringUTFChars(wzName,0);

	// print out requette
	cout << "dans wwi.dll : " << msg << endl;

	bstr_t rq = bstr_t(msg);
	bstr_t WZ_Name = bstr_t(wZName);

	// Libération de la chaîne C++  
	env->ReleaseStringUTFChars(requete,msg);
	env->ReleaseStringUTFChars(wzName, wZName);

	// initilisation du tableau de retour
	int size = 255;
	_jobjectArray* tab = env->NewObjectArray(size, env->FindClass("java/lang/String"), NULL);
	// initiatlisation du code d'erreur
	char * errStr = "1";


	
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
		//cout << "Query for operating system name failed." 
		//	<< " Error code = 0x" 
		//	<< hex << hres << endl;
		char *errMsg =   "Query for operating system name failed."; 
		pSvc->Release(); 
		pLoc->Release(); 
		CoUninitialize(); 
		env->SetObjectArrayElement(tab, 0, env->NewStringUTF(errStr));
		env->SetObjectArrayElement(tab, 1, env->NewStringUTF(errMsg));
		return tab;
	}


	// Step 7: ------------------------------------------------- 
	// Get the data from the query in step 6 ------------------- 

	IWbemClassObject *pclsObj; 
	ULONG uReturn = 0; 
	
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
		hr = pclsObj->Get(WZ_Name, 0, &vtProp, 0, 0);
		if (SUCCEEDED(hr))
		{
			char * asciStr = f_test_type_of_variant(vtProp);
			env->SetObjectArrayElement(tab, i++, env->NewStringUTF(asciStr));
		}
		VariantClear(&vtProp); 
	}

	// Cleanup 
	// ======== 

	//pSvc->Release(); 
	//pLoc->Release(); 
	pEnumerator->Release(); 
	pclsObj->Release(); 
	CoUninitialize(); 
// Program successfully completed. 
	return tab;
}



//JNIEXPORT jobjectArray JNICALL Java_pfe_migration_client_pre_service_WanduxWmiBridge_exec_1rq
//(JNIEnv *env, jobject obj, jstring rootPath, jstring requete, jstring wzName)
//{
//	HRESULT hres; 
//
//	// recuperation et preparation des parametres
//	const char* msg=env->GetStringUTFChars(requete,0);
//	const char* wZName=env->GetStringUTFChars(wzName,0);
//	const char* rootPATH=env->GetStringUTFChars(rootPath,0);
//	
//	// print out requette
//	cout << msg << endl;
//
//	bstr_t rq = bstr_t(msg);
//	bstr_t WZ_Name = bstr_t(wZName);
//	bstr_t ROOT_PATH = bstr_t(rootPATH);
//
//	// Libération de la chaîne C++  
//	env->ReleaseStringUTFChars(requete,msg);
//	env->ReleaseStringUTFChars(wzName, wZName);
//	env->ReleaseStringUTFChars(rootPath, rootPATH);
//
//
//	// initilisation du tableau de retour
//	int size = 255;
//	_jobjectArray* tab = env->NewObjectArray(size, env->FindClass("java/lang/String"), NULL);
//	// initiatlisation du code d'erreur
//	char * errStr = "1";
//
//	// Step 1: -------------------------------------------------- 
//	// Initialize COM. ------------------------------------------ 
//
//	hres =  CoInitializeEx(0, COINIT_MULTITHREADED); 
//	if (FAILED(hres)) 
//	{ 
//		//cout << "Failed to initialize COM library. Error code = 0x" 
//		//	<< hex << hres << endl; 
//		char *errMsg = "Failed to initialize COM library. Error code = 0x" ;
//		env->SetObjectArrayElement(tab, 0, env->NewStringUTF(errStr));
//		env->SetObjectArrayElement(tab, 1, env->NewStringUTF(errMsg));
//		return tab;
//	} 
//
//	// Step 2: -------------------------------------------------- 
//	// Set general COM security levels -------------------------- 
//	// Note: If you are using Windows 2000, you need to specify - 
//	// the default authentication credentials for a user by using 
//	// a SOLE_AUTHENTICATION_LIST structure in the pAuthList ---- 
//	// parameter of CoInitializeSecurity ------------------------ 
//
//	hres =  CoInitializeSecurity( 
//		NULL, 
//		-1,                          // COM authentication 
//		NULL,                        // Authentication services 
//		NULL,                        // Reserved 
//		RPC_C_AUTHN_LEVEL_DEFAULT,   // Default authentication 
//		RPC_C_IMP_LEVEL_IMPERSONATE, // Default Impersonation 
//		NULL,                        // Authentication info 
//		EOAC_NONE,                   // Additional capabilities 
//		NULL                         // Reserved 
//		); 
//
//	if (FAILED(hres)) 
//	{ 
//		//cout << "Failed to initialize security. Error code = 0x" 
//		//	<< hex << hres << endl; 
//		char *errMsg =  "Failed to initialize security. Error code = 0x" ;
//		CoUninitialize(); 
//		
//		env->SetObjectArrayElement(tab, 0, env->NewStringUTF(errStr));
//		env->SetObjectArrayElement(tab, 1, env->NewStringUTF(errMsg));
//		return tab;
//	} 
//
//	// Step 3: --------------------------------------------------- 
//	// Obtain the initial locator to WMI ------------------------- 
//
//	IWbemLocator *pLoc = NULL; 
//
//	hres = CoCreateInstance( 
//		CLSID_WbemLocator, 
//		0, 
//		CLSCTX_INPROC_SERVER, 
//		IID_IWbemLocator, (LPVOID *) &pLoc); 
//
//	if (FAILED(hres)) 
//	{ 
//		//cout << "Failed to create IWbemLocator object." 
//		//	<< " Err code = 0x" 
//		//	<< hex << hres << endl; 
//		char *errMsg =   "Failed to create IWbemLocator object."; 
//		CoUninitialize(); 
//
//		env->SetObjectArrayElement(tab, 0, env->NewStringUTF(errStr));
//		env->SetObjectArrayElement(tab, 1, env->NewStringUTF(errMsg));
//		return tab;
//	} 
//
//
//	// Step 4: ----------------------------------------------------- 
//	// Connect to WMI through the IWbemLocator::ConnectServer method 
//
//	IWbemServices *pSvc = NULL; 
//
//	// Connect to the root\cimv2 namespace with 
//	// the current user and obtain pointer pSvc 
//	// to make IWbemServices calls. 
//	hres = pLoc->ConnectServer( 
//		ROOT_PATH, // Object path of WMI namespace 
//		NULL,                    // User name. NULL = current user 
//		NULL,                    // User password. NULL = current 
//		0,                       // Locale. NULL indicates current 
//		NULL,                    // Security flags. 
//		0,                       // Authority (e.g. Kerberos) 
//		0,                       // Context object 
//		&pSvc                    // pointer to IWbemServices proxy 
//		); 
//
//	if (FAILED(hres)) 
//	{ 
//		//cout << "Could not connect. Error code = 0x" 
//		//	<< hex << hres << endl; 
//		char *errMsg =   "Could not connect. Error code = 0x"; 
//		pLoc->Release(); 
//		CoUninitialize();
//		env->SetObjectArrayElement(tab, 0, env->NewStringUTF(errStr));
//		env->SetObjectArrayElement(tab, 1, env->NewStringUTF(errMsg));
//		return tab;
//	} 
//
//	wcout << "Connected to " << ROOT_PATH << endl; 
//
//	// Step 5: -------------------------------------------------- 
//	// Set security levels on the proxy ------------------------- 
//
//
//	hres = CoSetProxyBlanket( 
//		pSvc,                        // Indicates the proxy to set 
//		RPC_C_AUTHN_WINNT,           // RPC_C_AUTHN_xxx 
//		RPC_C_AUTHZ_NONE,            // RPC_C_AUTHZ_xxx 
//		NULL,                        // Server principal name 
//		RPC_C_AUTHN_LEVEL_CALL,      // RPC_C_AUTHN_LEVEL_xxx 
//		RPC_C_IMP_LEVEL_IMPERSONATE, // RPC_C_IMP_LEVEL_xxx 
//		NULL,                        // client identity 
//		EOAC_NONE                    // proxy capabilities 
//		); 
//
//	if (FAILED(hres)) 
//	{ 
//		char *errMsg =   "Could not set proxy blanket. Error code = AFAIRE"; 
//		pSvc->Release(); 
//		pLoc->Release(); 
//		CoUninitialize(); 
//		
//		env->SetObjectArrayElement(tab, 0, env->NewStringUTF(errStr));
//		env->SetObjectArrayElement(tab, 1, env->NewStringUTF(errMsg));
//		return tab;
//	} 
//
//	// Step 6: -------------------------------------------------- 
//	// Use the IWbemServices pointer to make requests of WMI ---- 
//
//	// For example, get the name of the operating system 
//	IEnumWbemClassObject* pEnumerator = NULL; 
//	hres = pSvc->ExecQuery( 
//		bstr_t("WQL"), 
//		bstr_t(rq), 
//		WBEM_FLAG_FORWARD_ONLY | WBEM_FLAG_RETURN_IMMEDIATELY, 
//		NULL, 
//		&pEnumerator); 
//
//	if (FAILED(hres)) 
//	{ 
//		//cout << "Query for operating system name failed." 
//		//	<< " Error code = 0x" 
//		//	<< hex << hres << endl;
//		char *errMsg =   "Query for operating system name failed."; 
//		pSvc->Release(); 
//		pLoc->Release(); 
//		CoUninitialize(); 
//		env->SetObjectArrayElement(tab, 0, env->NewStringUTF(errStr));
//		env->SetObjectArrayElement(tab, 1, env->NewStringUTF(errMsg));
//		return tab;
//	}
//
//	// Step 7: ------------------------------------------------- 
//	// Get the data from the query in step 6 ------------------- 
//
//	IWbemClassObject *pclsObj; 
//	ULONG uReturn = 0; 
//
//
//	
//	int i = 0;
//	while(pEnumerator)
//	{
//		HRESULT hr = pEnumerator->Next(WBEM_INFINITE, 1, 
//			&pclsObj, &uReturn); 
//		if(0 == uReturn) 
//		{ 
//			break; 
//		} 
//		VARIANT vtProp; 
//		VariantInit(&vtProp); 
//		// Get the value of the Name property 
//		hr = pclsObj->Get(WZ_Name, 0, &vtProp, 0, 0);
//		if (SUCCEEDED(hr))
//		{
//			char * asciStr = f_test_type_of_variant(vtProp);
//			env->SetObjectArrayElement(tab, i++, env->NewStringUTF(asciStr));
//		}
//		VariantClear(&vtProp); 
//	}
//
//	// Cleanup 
//	// ======== 
//
//	pSvc->Release(); 
//	pLoc->Release(); 
//	pEnumerator->Release(); 
//	pclsObj->Release(); 
//	CoUninitialize(); 
//// Program successfully completed. 
//	return tab;
//}



