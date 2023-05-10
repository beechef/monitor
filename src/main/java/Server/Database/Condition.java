package Server.Database;

public class Condition {
    private final String _left;
    private final Operator _operator;
    private final String _right;

    private final CombineCondition _combineCondition;

    public Condition(String left, Operator operator, String right, CombineCondition combineCondition) {
        this._left = left;
        this._operator = operator;
        this._right = right;
        this._combineCondition = combineCondition;
    }

    private String operatorToString(Operator operator) {
        switch (operator) {

            case Equal -> {
                return "=";
            }
            case NotEqual -> {
                return "!=";
            }
            case Lesser -> {
                return "<";
            }
            case LesserEqual -> {
                return "<=";
            }
            case Greater -> {
                return ">";
            }
            case GreaterEqual -> {
                return ">=";
            }

            default -> {
                return "";
            }
        }
    }

    private String combineConditionToString(CombineCondition combineCondition) {
        switch (combineCondition) {

            case AND -> {
                return " AND ";
            }
            case OR -> {
                return " OR ";
            }
            default -> {
                return " ";
            }
        }
    }

    @Override
    public String toString() {
        return _left + operatorToString(_operator) + "\"" + _right + "\"" + combineConditionToString(_combineCondition);
    }
}
