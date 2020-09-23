package net.arsentic.arsenticskyblock


enum class Role(@field:Getter var rank: Int) {
    Owner(4), CoOwner(3), Moderator(2), Member(1), Visitor(-1);

    override fun toString(): String {
        return if (getMessages().roles.containsKey(this)) {
            getMessages().roles.get(this)
        } else name
    }

    companion object {
        fun getViaRank(i: Int): Role? {
            for (role in values()) {
                if (role.rank == i) {
                    return role
                }
            }
            return null
        }
    }
}