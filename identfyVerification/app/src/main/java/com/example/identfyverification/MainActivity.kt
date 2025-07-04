package com.example.identfyverification

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.idenfy.idenfySdk.CoreSdkInitialization.IdenfyController
import com.idenfy.idenfySdk.api.initialization.IdenfySettingsV2
import com.idenfy.idenfySdk.api.response.AutoIdentificationStatus
import com.idenfy.idenfySdk.api.response.IdenfyIdentificationResult
import com.idenfy.idenfySdk.api.response.ManualIdentificationStatus


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val idenfySettingsV2: IdenfySettingsV2 = IdenfySettingsV2.IdenfyBuilderV2()
            .withAuthToken("AUTH_TOKEN")
            .build()

        IdenfyController.getInstance().initializeIdenfySDKV2WithManual(this,
            IdenfyController.IDENFY_REQUEST_CODE,
            idenfySettingsV2);
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IdenfyController.IDENFY_REQUEST_CODE) {
            if (resultCode == IdenfyController.IDENFY_IDENTIFICATION_RESULT_CODE) {
                val idenfyIdentificationResult: IdenfyIdentificationResult =
                    data!!.getParcelableExtra(IdenfyController.IDENFY_IDENTIFICATION_RESULT)!!
                if (idenfyIdentificationResult != null) {
                    when (idenfyIdentificationResult.manualIdentificationStatus) {
                        ManualIdentificationStatus.APPROVED -> {
                            toast("manualApproved")
                        }
                        ManualIdentificationStatus.FAILED -> {
                            toast("manualFailed")

                        }
                        ManualIdentificationStatus.WAITING -> {
                            toast("manualWaiting")

                        }
                        ManualIdentificationStatus.INACTIVE -> {
                            toast("manualInactive")

                        }
                    }
                    when (idenfyIdentificationResult.autoIdentificationStatus) {
                        AutoIdentificationStatus.APPROVED -> {
                            toast("autoApproved")

                        }
                        AutoIdentificationStatus.FAILED -> {
                            toast("autofailed")

                        }
                        AutoIdentificationStatus.UNVERIFIED -> {
                            toast("autoUNVERIFIED")

                        }
                    }
                }
                Toast.makeText(this, idenfyIdentificationResult.toString(), Toast.LENGTH_LONG)
                    .show()
                Log.d("SDKResponse", idenfyIdentificationResult.toString())
            }
        }
    }


    fun toast(msg:String){
        Toast.makeText(this," " + msg,Toast.LENGTH_LONG).show()
        Log.d("MSG", "MSG " + msg.toString())
    }
}