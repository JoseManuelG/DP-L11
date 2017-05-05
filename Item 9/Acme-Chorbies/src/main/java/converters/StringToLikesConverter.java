
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.LikesRepository;
import domain.Likes;

@Component
@Transactional
public class StringToLikesConverter implements Converter<String, Likes> {

	@Autowired
	LikesRepository	likesRepository;


	@Override
	public Likes convert(final String text) {
		Likes result;
		int id;

		try {
			id = Integer.valueOf(text);
			this.likesRepository.findAll();
			result = this.likesRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
