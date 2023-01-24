package ar.com.school.management.models.entity

import ar.com.school.management.utils.Role
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
@MappedSuperclass
open class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long?,

    @Column(name = "social_security_number", nullable = false) //unique = true
    open var socialSecurityNumber: Int?,

    @Column(nullable = false)
    open var name: String?,

    @Column(nullable = false)
    open var surname: String?,

    @Column(name = "phone_number",  nullable = false) //unique = true,)
    open var phone: Int? = null,

    @Column(nullable = false) //unique = true
    @Email
    open var email: String?,

    @Column(name = "password", nullable = false)
    open var pw: String?,

    @Enumerated(EnumType.STRING)
    open var role: Role?,

    ): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf(SimpleGrantedAuthority(role!!.name))

    override fun getPassword(): String? = pw
    override fun getUsername(): String? = email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}