package persistence;

import java.util.List;

import objects.FullMathExpression;

public interface MathRepository {

	public long save(FullMathExpression expression);
	public FullMathExpression findById(long id);
	public List<FullMathExpression> findAllWithThisComplexity(int complexity);
	public List<FullMathExpression> findAllWhitinThisIdRange(long start,long end);
	public boolean delete(long id);
	public List<FullMathExpression> fetchAll();
}
