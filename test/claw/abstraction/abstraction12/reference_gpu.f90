MODULE mo_column

CONTAINS
 FUNCTION compute_column ( nz , b , q , t , nproma ) RESULT(r)
  INTEGER , INTENT(IN) :: nz
  INTEGER , INTENT(IN) :: b
  REAL , INTENT(INOUT) :: t ( 1 : b , 1 : nproma )
  REAL , INTENT(INOUT) :: q ( 1 : b , 1 : nproma )
  INTEGER , INTENT(IN) :: nproma
  INTEGER :: k
  REAL :: c
  INTEGER :: r
  INTEGER :: proma

!$acc data present(t,q)
!$acc parallel
!$acc loop gang vector
  DO proma = 1 , nproma , 1
   c = 5.345
!$acc loop seq
   DO k = 2 , nz , 1
    t ( k , proma ) = c * k
    q ( k , proma ) = q ( k - 1 , proma ) + t ( k , proma ) * c
   END DO
   q ( nz , proma ) = q ( nz , proma ) * c
  END DO
!$acc end parallel
!$acc end data
 END FUNCTION compute_column

 SUBROUTINE compute ( nz , b , q , t , nproma )

  INTEGER , INTENT(IN) :: nz
  INTEGER , INTENT(IN) :: b
  REAL , INTENT(INOUT) :: t ( 1 : b , 1 : nproma )
  REAL , INTENT(INOUT) :: q ( 1 : b , 1 : nproma )
  INTEGER , INTENT(IN) :: nproma
  INTEGER :: result

  result = compute_column ( nz , b , q , t , nproma = nproma )
 END SUBROUTINE compute

END MODULE mo_column

