package tk.tinkerit.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tk.tinkerit.demo.model.Cat;
import tk.tinkerit.demo.repository.CatRepository;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class CatTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private CatRepository catRepository;

	@BeforeEach
    public void setUp() {
		Stream.of(
			"Felix",
			"Garfield",
			"Whiskers"
		).forEach(n -> catRepository.save(new Cat(n)));
	}

	@Test
	public void catsReflectedInRead() throws Exception {
		MediaType halJson = MediaType.parseMediaType("application/hal+json");
		this.mvc
			.perform(get("/cats"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(halJson))
			.andExpect(result -> {
				String content = result.getResponse().getContentAsString();
				assertEquals("3", content.split("totalElements")[1].split(":")[1].trim().split(",")[0]);
			});
	}

}
