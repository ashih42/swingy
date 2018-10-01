package ashih.swingy.model;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class ValidationManager
{
	private static final ValidationManager instance = new ValidationManager();

	public static ValidationManager getInstance()
	{
		return (ValidationManager.instance);
	}

	private final Validator validator;

	public ValidationManager()
	{
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		this.validator = validatorFactory.getValidator();
	}

	public boolean validateHero(Hero hero)
	{
		Set<ConstraintViolation<Hero>> violations = this.validator.validate(hero);

		if (violations.size() == 0)
			return (true);
		else
		{
			for (ConstraintViolation<Hero> violation : violations)
				System.out.println("Constraint Violation: " + violation.getMessage());
			return (false);
		}
	}

}
