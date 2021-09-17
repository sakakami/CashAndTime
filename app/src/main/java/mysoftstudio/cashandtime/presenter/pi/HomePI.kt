package mysoftstudio.cashandtime.presenter.pi

import mysoftstudio.cashandtime.gson.CreateMemberG
import mysoftstudio.cashandtime.gson.MemberG

interface HomePI {
    fun handleFinishCreate(createMemberG: CreateMemberG)
    fun handleMemberData(memberG: MemberG)
}