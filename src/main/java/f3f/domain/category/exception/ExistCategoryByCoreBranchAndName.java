package f3f.domain.category.exception;

public class ExistCategoryByCoreBranchAndName extends IllegalArgumentException {

    public ExistCategoryByCoreBranchAndName() {
    }

    public ExistCategoryByCoreBranchAndName(String s) {
        super(s);
    }
}
