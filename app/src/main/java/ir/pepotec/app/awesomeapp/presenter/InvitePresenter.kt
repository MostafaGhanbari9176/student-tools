package ir.pepotec.app.awesomeapp.presenter

import ir.pepotec.app.awesomeapp.model.ServerRes
import ir.pepotec.app.awesomeapp.model.invite.Invite
import ir.pepotec.app.awesomeapp.model.user.UserDb
import ir.pepotec.app.awesomeapp.view.uses.AF

class InvitePresenter(private val listener: Res) {

    interface Res {
        fun result(ok: Boolean, message: String) {}
    }

    private val p = UserDb().getUserPhone()
    private val ac = UserDb().getUserApiCode()

    fun add(groupId: Int, userId: Int) {
        Invite(object : Invite.Res {
            override fun result(res: ServerRes?) {
                listener.result(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1))
            }
        }).add(p, ac, groupId, userId)
    }

    fun accept(inviteId: Int) {
        Invite(object : Invite.Res {
            override fun result(res: ServerRes?) {
                listener.result(res?.code == ServerRes.ok, res?.message ?: AF().serverMessage(res?.code ?: -1))
            }
        }).accept(p, ac, inviteId)
    }

}