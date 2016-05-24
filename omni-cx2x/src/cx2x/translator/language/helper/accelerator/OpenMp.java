/*
 * This file is released under terms of BSD license
 * See LICENSE file for more information
 */

package cx2x.translator.language.helper.accelerator;

import cx2x.translator.language.helper.target.Target;

/**
 * OpenMP base accelerator directive generator. Implements everything that is
 * common for host and device target.
 *
 * @author clementval
 */
public class OpenMp extends AcceleratorGenerator {

  private static final String OPENMP_PREFIX = "omp";
  private static final String OPENMP_DECLARE = "delcare";
  private static final String OPENMP_TARGET = "target";
  private static final String OPENMP_PARALLEL = "parallel";
  private static final String OPENMP_PRIVATE = "private";
  private static final String OPENMP_DO = "do";
  private static final String OPENMP_END = "end";

  /**
   * Constructs a new object with the given target.
   *
   * @param target Target for which the directive must be generated.
   */
  protected OpenMp(Target target) {
    super(target);
  }

  @Override
  protected String getPrefix(){
    return OPENMP_PREFIX;
  }

  @Override
  protected String getStartParellelDirective() {
    if(_target == Target.CPU){
      // TODO check syntax and variant
      return String.format(FORMAT2,
          OPENMP_PREFIX, OPENMP_PARALLEL);
    } else {
      //!$omp target parallel do
      return String.format(FORMAT3,
          OPENMP_PREFIX, OPENMP_TARGET, OPENMP_PARALLEL);
    }
  }

  @Override
  public String getEndParellelDirective() {
    //!$omp end target parallel do
    return String.format(FORMAT4,
        OPENMP_PREFIX, OPENMP_END, OPENMP_TARGET, OPENMP_PARALLEL);
  }

  @Override
  public String getSingleDirective(String clause) {
    return String.format(FORMAT2, OPENMP_PREFIX, clause);
  }

  @Override
  protected String getParallelKeyword() {
    return OPENMP_PARALLEL;
  }

  @Override
  protected String getPrivateClause(String var) {
    return String.format(FORMATPAR, OPENMP_PRIVATE, var);
  }

  @Override
  protected String getRoutineDirective(){
    return String.format(FORMAT3, OPENMP_PREFIX, OPENMP_DECLARE, OPENMP_TARGET);
  }

  @Override
  public boolean isCompileGuard(String rawDirective){
    return rawDirective.toLowerCase().startsWith(OPENMP_PREFIX) &&
        rawDirective.toLowerCase().contains(COMPILE_GUARD);
  }

  @Override
  public AcceleratorDirective getDirectiveLanguage(){
    return AcceleratorDirective.OPENMP;
  }

  @Override
  protected String getStartLoopDirective(int value) {
    return String.format(FORMAT3, OPENMP_PREFIX, OPENMP_PARALLEL, OPENMP_DO);
  }

  @Override
  protected String getEndLoopDirective() {
    return null;
  }
}
