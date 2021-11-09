package com.selfi.alarm

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.selfi.R
import com.selfi.ui.activity.TodolistActivity
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class AlarmReceiver: BroadcastReceiver() {
    val TYPE_ONE_TIME: String = "Kerjakan Tugasmu Sekarang!"
    val EXTRA_MESSAGE: String = "message"
    val EXTRA_TYPE: String = "type"
    val CHANNEL_ID: String = "channel_notif_alarm"
    val CHANNEL_NAME: CharSequence = "Alarm Channel"



    val ID: Int = 100



    override fun onReceive(context: Context?, intent: Intent?) {
        val type: String? = intent?.getStringExtra(EXTRA_TYPE)
        val message: String? = intent?.getStringExtra(EXTRA_MESSAGE)

        val title: String = TYPE_ONE_TIME
        val notifId: Int = ID

        showAlarmNotification(context!!, title, message!!, notifId)
    }


    fun showAlarmNotification(context: Context, title: String, message: String,notifId: Int ){
        val notificationManager:NotificationManager =  context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val mp: MediaPlayer = MediaPlayer.create(context, alarmSound)
        mp.start()

        val i = Intent(context, TodolistActivity:: class.java)
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val pendingIntent = PendingIntent.getActivity(context, 0, i, 0)

        val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.logoo)
            .setContentTitle(title)
            .setContentText(message)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern
            mBuilder.setChannelId(CHANNEL_ID)

            if(notificationManager != null){
                notificationManager.createNotificationChannel(notificationChannel)
            }
        }

        val notification : Notification = mBuilder.build()
        if(notificationManager != null){
            notificationManager.notify(notifId, notification)
        }
    }


    fun isAlarmSet(context: Context, type: String): Boolean{
        val intent: Intent = Intent(context, AlarmReceiver:: class.java)
        val requestCode: Int = ID

        return PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_NO_CREATE ) != null
    }

    fun cancelAlarm(context: Context, type: String){
        val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent: Intent = Intent(context, AlarmReceiver :: class.java)
        val requestCode: Int = ID
        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(context, requestCode,intent,PendingIntent.FLAG_CANCEL_CURRENT)

        if(alarmManager != null){
            alarmManager.cancel(pendingIntent)
        }
    }


    fun isDateInvalid(date: String, format: String): Boolean{
        val df: DateFormat = SimpleDateFormat(format, Locale.getDefault())
        df.setLenient(false)
         try {
            df.parse(date)
            return false
        } catch (e : ParseException){
            e.printStackTrace()
            return true
        }
    }


    fun setAlarm(context: Context, type: String, date: String, time: String, message: String){
        if(isDateInvalid(date, "yyyy-MM-dd") || isDateInvalid(time, "HH:mm")) return

        val alarmManager: AlarmManager =
            (context.getSystemService(Context.ALARM_SERVICE) as AlarmManager)!!
        val intent: Intent = Intent(context, AlarmReceiver :: class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_TYPE, type)

        val dateArray = date.split("-")
        val timeArray = time.split(":")

        val c: Calendar = Calendar.getInstance()
        c.set(Calendar.YEAR, Integer.parseInt(dateArray[0]))
        c.set(Calendar.MONTH, Integer.parseInt(dateArray[1]) -1)
        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateArray[2]))

        c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        c.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        c.set(Calendar.SECOND, 0)

        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(context, ID, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        if(alarmManager != null){
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, c.timeInMillis, pendingIntent)
        }

    }
}