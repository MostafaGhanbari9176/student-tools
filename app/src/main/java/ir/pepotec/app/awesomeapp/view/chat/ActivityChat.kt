package ir.pepotec.app.awesomeapp.view.chat

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import ir.pepotec.app.awesomeapp.R
import ir.pepotec.app.awesomeapp.model.groupChat.GroupChatData
import ir.pepotec.app.awesomeapp.model.student.profile.StudentProfile
import ir.pepotec.app.awesomeapp.presenter.GroupChatPresenter
import ir.pepotec.app.awesomeapp.presenter.student.StudentProfilePresenter
import ir.pepotec.app.awesomeapp.view.chat.chatList.FragmentChat
import ir.pepotec.app.awesomeapp.view.chat.messageList.FragmentMessageList
import ir.pepotec.app.awesomeapp.view.main.search.ActivitySearch
import ir.pepotec.app.awesomeapp.view.student.profile.FragmentMyProfile
import ir.pepotec.app.awesomeapp.view.uses.AF
import ir.pepotec.app.awesomeapp.view.uses.App
import ir.pepotec.app.awesomeapp.view.uses.MyActivity
import org.jetbrains.anko.toast

class ActivityChat : MyActivity() {

    var chat_id = -1
    var kind_id = ""
    private var imageData: Intent? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_common)
        App.instanse = this@ActivityChat
        chat_id = intent?.extras?.getInt("chat_id") ?: -1
        kind_id = intent?.extras?.getString("kind_id") ?: ""
        if (chat_id == -1)
            changeView(FragmentChat())
        else
            changeView(FragmentMessageList().apply {
                chat_id = this@ActivityChat.chat_id; kind_id = this@ActivityChat.kind_id
            })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_chat, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.chatMenuAddGroup -> changeView(FragmentAddGroupChat())
            R.id.chatMenuSearchStudent -> {
                startActivity(Intent(this, ActivitySearch::class.java).apply { putExtra("flag", "student") })
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        App.instanse = this
        StudentProfilePresenter(object : StudentProfilePresenter.StudentProfileResult {}).changeOnline(1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                1 -> (backHistory.peek() as FragmentMessageList).fileChoosed(data?.getStringExtra("path") ?: "")
                2 -> {
                    imageData = data
                    cropImage(data?.data)
                }
                3 -> {
                    changeImage(data)
                }
            }
        }else if (imageData != null)
            changeImage(imageData)
    }

    private fun cropImage(uri: Uri?) {
        val cropIntent = Intent("com.android.camera.action.CROP", uri)
        cropIntent.apply {
            setDataAndType(uri, "image/*");
            putExtra("crop", "true")
            putExtra("outputX", 512)
            putExtra("outputY", 512)
            putExtra("aspectX", 1)
            putExtra("aspectY", 1)
            putExtra("scaleUpIfNeeded", true)
            putExtra("return-data", true)
        }
        startActivityForResult(cropIntent, 3)
    }

    private fun changeImage(data: Intent?) {
        val fragment = backHistory.peek() as FragmentGroupChatData
        val uri = data?.data
        val bitmap: Bitmap
        bitmap = if (uri != null)
            MediaStore.Images.Media.getBitmap(contentResolver, uri)
        else {
            val extra = data?.extras
            extra!!.getParcelable("data")
        }

        val f = AF().convertBitMapToFile(bitmap, this, "groupImg")

        GroupChatPresenter(object:GroupChatPresenter.Res{
            override fun result(ok: Boolean, message: String, data: GroupChatData?) {
                if(ok)
                    fragment.setImage()
                toast(message)
            }
        }).uploadImage(fragment.groupId, f)

    }

    override fun onPause() {
        super.onPause()
        StudentProfilePresenter(object : StudentProfilePresenter.StudentProfileResult {}).changeOnline(0)
    }

}