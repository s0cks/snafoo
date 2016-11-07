/**
 * Since this package contains simple interfaces that are extremely obvious I'll just breifly elaborate on them.
 * 
 * Each interface in this package are just declarations for JPA repositories. The name schema represents the repositories element type i.e:
 * UserRepository - JPA repository for {@link io.github.s0cks.snafoo.model.domain.User}
 * 
 * Any more detailed information can be found just by reading the declarations - the functions contained in there should be verbose enough to see what they do
 * 
 * Side Note:
 *   Why does Spring? JPA do this weird (what I am assuming is) class loader magic with either generation via ASM or mixing in functonalities with ASM either way its weird.
 *
 */
package io.github.s0cks.snafoo.repo;