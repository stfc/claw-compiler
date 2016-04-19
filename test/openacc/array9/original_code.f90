! Simple program to test the array notation to do loop directive
! test block transformation of the array-transform directive

PROGRAM array4_test
  CALL claw_test
END

! Before the transformation
SUBROUTINE claw_test
  INTEGER, DIMENSION(0:10) :: vec1
  INTEGER, DIMENSION(0:10) :: vec2
  INTEGER

  vec1(:) = 0;
  vec2(:) = 100;

  PRINT*,vec1
  PRINT*,vec2

  !$claw array-transform
  vec1(:) = vec2(:) + 10
  vec2(:) = vec1(1:10) + 10
  !$claw end array-transform

  PRINT*,vec1
  PRINT*,vec2


  PRINT*,SUM(vec1)
  PRINT*,SUM(vec2)
END SUBROUTINE claw_test