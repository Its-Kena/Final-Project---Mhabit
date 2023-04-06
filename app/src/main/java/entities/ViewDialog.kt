package entities

import android.app.Activity
import android.app.Dialog
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.TextView
import com.example.test.R

class ViewDialog {
    fun showAddMovieDialog(activity: Activity?) {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.resetpass_popup)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val dialogBtn_remove = dialog.findViewById<TextView>(R.id.txtClose)
        dialogBtn_remove.setOnClickListener {
            dialog.dismiss()
            activity!!.finish()
        }
        dialog.show()
    }
}