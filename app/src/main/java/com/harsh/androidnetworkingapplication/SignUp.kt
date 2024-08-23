package com.harsh.androidnetworkingapplication

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONObject

class SignUp : AppCompatActivity() {

    var edtFirstName : EditText? = null
    var edtLastName : EditText? = null
    var edtUserName : EditText? = null
    var edtEmail : EditText? = null
    var edtPassword : EditText? = null
    var edtMobileNumber : EditText? = null
    var edtCountryCode : EditText? = null
    var spinnerUserType : Spinner? = null
    var btnSubmit : Button? = null

    private val TAG = SignUp::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)



        edtFirstName = findViewById(R.id.edtFirstName)
        edtLastName = findViewById(R.id.edtLastName)
        edtUserName = findViewById(R.id.edtUserName)
        edtEmail = findViewById(R.id.edtEmail)
        edtPassword = findViewById(R.id.edtPassword)
        edtMobileNumber = findViewById(R.id.edtMobileNumber)
        edtCountryCode = findViewById(R.id.edtCountryCode)
        spinnerUserType = findViewById(R.id.spinnerUserType)
        btnSubmit = findViewById(R.id.btnSubmit)

        val spinner: Spinner = findViewById(R.id.spinnerUserType)

        val arrList : ArrayList<String> = ArrayList()
        arrList.add("Customer")

        var adapter : ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrList)
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {

                val item = adapterView.getItemAtPosition(position).toString()
                Toast.makeText(this@SignUp, "Selected Item: $item", Toast.LENGTH_SHORT).show()

            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }

        btnSubmit?.setOnClickListener()
        {
            AndroidNetworking.initialize(applicationContext)
            AndroidNetworking.post("http://153.92.4.13:6215/api/app/auth/sign-up")
                .addBodyParameter("firstName",edtFirstName?.text.toString())
                .addBodyParameter("lastName",edtLastName?.text.toString())
                .addBodyParameter("userName",edtUserName?.text.toString())
                .addBodyParameter("email",edtEmail?.text.toString())
                .addBodyParameter("password",edtPassword?.text.toString())
                .addBodyParameter("mobileNumber",edtMobileNumber?.text.toString())
                .addBodyParameter("countryCode",edtCountryCode?.text.toString())
                .addBodyParameter("user_type",spinnerUserType?.selectedItem.toString())
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener{
                    override fun onResponse(response: JSONObject?) {
                        Log.d(TAG, "onReesponse: Status : ${response?.getJSONObject("message")}")
                        Toast.makeText(applicationContext,"Status : ${response?.getJSONObject("message")}",Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(anError: ANError?) {
                        Log.d(TAG, "onErrror: ${anError?.errorBody}")
                        Toast.makeText(applicationContext,"Status : ${anError?.errorBody}",Toast.LENGTH_SHORT).show()
                    }

                })
        }




    }
}