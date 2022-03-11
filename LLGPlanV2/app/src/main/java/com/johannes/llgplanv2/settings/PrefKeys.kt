package com.johannes.llgplanv2.settings

class PrefKeys {
    companion object {
        const val secretsEnabled = "secrets_enabled"
        val secretActivationStrings = listOf(
            "secrets 1", "secrets on"
        )
        val secretDeactivationStrings = listOf(
            "secrets 0", "secrets off"
        )

        const val firstLaunch = "first_launch"
        const val slpLoginDone = "slp_login_done"
        const val dsbLoginDone = "dsb_login_done"
    }
}