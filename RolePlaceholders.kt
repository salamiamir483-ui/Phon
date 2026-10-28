package ir.privatesmsvault.platform

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telecom.Call
import android.telecom.CallScreeningService

/** Placeholder غیرفعال؛ در Manifest ثبت نشده و هیچ پیامکی را دریافت یا پردازش نمی‌کند. */
class SmsDeliverReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) { /* TODO: نقش و مجوز کاربر را بررسی کنید؛ هیچ ارسال شبکه‌ای نداریم. */ }
}

/** Placeholder غیرفعال؛ در Manifest ثبت نشده است. */
class MmsDeliverReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) { /* intentionally empty placeholder */ }
}

/** Placeholder غیرفعال؛ در Manifest ثبت نشده است. */
class RespondViaMessageService : android.app.Service() {
    override fun onBind(intent: Intent?) = null
}

/** بدون ضبط، شنود یا ذخیرهٔ صدا؛ Placeholder غیرفعال؛ در Manifest ثبت نشده و تماس را غربالگری نمی‌کند. */
class VaultCallScreeningService : CallScreeningService() {
    override fun onScreenCall(callDetails: Call.Details) { /* TODO: غربالگری با API رسمی و پاسخ به‌موقع؛ هرگز ضبط مکالمه نکنید. */ }
}
