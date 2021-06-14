package hr.sztfr.sztfr_android.ui.settings

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.R.string
import hr.sztfr.sztfr_android.ui.MainActivity
import hr.sztfr.sztfr_android.util.isInFavourites

class CreateNotification {
    companion object {
        private const val CHANNEL_ID = "hr.sztfr"
        private const val NOTIFICATION_ID = 1234


        fun createNotificationChannel(activity: Activity?, docId: String) {
            val isInFAv = isInFavourites(docId)
            val x = (string.notifications)      //marina!!! tu treba nekako dodat getString, ne znam kako
            val canSend = true//x.equals('Y')

            if (!isInFAv && canSend) {

                Handler().postDelayed({
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val name = string.channel_name
                        val descriptionText = string.channel_description
                        val importance = NotificationManager.IMPORTANCE_DEFAULT
                        val channel =
                            NotificationChannel(CHANNEL_ID, name.toString(), importance).apply {
                                description = descriptionText.toString()
                            }

                        val notificationManager: NotificationManager =
                            activity?.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
                        notificationManager.createNotificationChannel(channel)

                        val intentNottification = Intent(activity, MainActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                        val pendingIntent: PendingIntent =
                            PendingIntent.getActivity(activity, 0, intentNottification, 0)

                        val builder = activity.let {
                            NotificationCompat.Builder(
                                it,
                                CHANNEL_ID
                            )
                                .setSmallIcon(R.drawable.ic_launcher_foreground)
                                .setContentTitle(string.notification_title.toString())
                                .setContentText(string.notification_text.toString())
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true)
                        }
                        notificationManager.notify(NOTIFICATION_ID, builder?.build())
                    }
                }, 5000)
            }
        }
    }


}