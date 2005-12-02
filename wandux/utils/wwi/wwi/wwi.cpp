// This is the main DLL file.

#include "stdafx.h"

#include "wwi.h"
#include <iostream>
#include <fstream>

using namespace std;

JNIEXPORT void JNICALL Java_pfe_migration_client_pre_service_WanduxWmiBridge_toto
  (JNIEnv *, jobject)
{
 cout << "marche bien" << endl;
}