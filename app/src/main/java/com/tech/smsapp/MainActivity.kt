package com.tech.smsapp

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


       btn_sms.setOnClickListener {
    var sms_status=ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS)
    if(sms_status==PackageManager.PERMISSION_GRANTED)
    {
        sendSms()
    }
    else{
        ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.SEND_SMS),101)
    }

}
btn_call.setOnClickListener {
    var call_status=ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)
    if(call_status==PackageManager.PERMISSION_GRANTED)
    {
        call()
    }
    else{
        ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.CALL_PHONE),201)
    }
}

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==101&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            sendSms()
        }else if(requestCode==201&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            call()
        }else
        {
            Toast.makeText(this,"user is not alllowed to call or send an sms",Toast.LENGTH_LONG).show()
        }
    }
    private fun call() {

        var mobile_number=et_mobile_number.text.toString()

        if(mobile_number.isEmpty())
        {
            Toast.makeText(this,"please enter the mobile number first",Toast.LENGTH_LONG).show()
        }else
        {
            var intent=Intent()
            intent.action=Intent.ACTION_CALL
            intent.setData(Uri.parse("tel:"+mobile_number))
            startActivity(intent)
        }
    }

    private fun sendSms() {
        var smsManager=SmsManager.getDefault()

        var mobile_number=et_mobile_number.text.toString()
        var message=et_message.text.toString()

       var numbers= mobile_number.split(",")

        for(number in numbers)
        {
            if(mobile_number.isEmpty())
            {
                Toast.makeText(this,"please enter the mobile number first",Toast.LENGTH_LONG).show()
            }else if(message.isEmpty()) {
                Toast.makeText(this,"please write your message first",Toast.LENGTH_LONG).show()

            }else
            {

                var sent_intent= Intent(this,Send::class.java)
                var pi=PendingIntent.getActivity(this,0,sent_intent,0)

                var delivered_intent= Intent(this,Delivered::class.java)
                var di=PendingIntent.getActivity(this,0,delivered_intent,0)

                smsManager.sendTextMessage(number,null,message,pi,di)
            }
        }

    }
}