package ar.com.school.management.models.entity

import ar.com.school.management.utils.Role
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    var id: Long?,

    @Column(name = "social_security_number", unique = true, nullable = false)
    var socialSecurityNumber: Int?,

    @Column(nullable = false)
    var name: String?,

    @Column(nullable = false)
    var surname: String?,

    @Column(name = "phone_number", unique = true, nullable = false)
    var phone: Int? = null,

    @Column(unique = true, nullable = false)
    @Email
    var email: String?,

    @Column(name = "password", nullable = false)
    var pw: String?,

    @Enumerated(EnumType.STRING)
    var role: Role?

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