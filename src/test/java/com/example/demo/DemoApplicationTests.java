import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

	private final SubjectService subjectService;

	@Autowired
	public SubjectController(SubjectService subjectService) {
		this.subjectService = subjectService;
	}

	@GetMapping
	public ResponseEntity<Response<List<Subject>>> getAllSubjects() {
		Response<List<Subject>> response = subjectService.findAll();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response<Subject>> getSubjectById(@PathVariable Long id) {
		Response<Subject> response = subjectService.findById(id);
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<Response<Subject>> createSubject(@RequestBody Subject subject) {
		Response<Subject> response = subjectService.save(subject);
		return ResponseEntity.status(response.isSuccess() ? 201 : 400).body(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Response<Subject>> updateSubject(@PathVariable Long id, @RequestBody Subject subject) {
		Response<Subject> response = subjectService.findById(id);
		if (response.isSuccess()) {
			subject.setId(id); // Устанавливаем ID для обновления
			Response<Subject> updatedResponse = subjectService.save(subject);
			return ResponseEntity.ok(updatedResponse);
		} else {
			return ResponseEntity.status(404).body(response);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response<String>> deleteSubject(@PathVariable Long id) {
		Response<String> response = subjectService.deleteById(id);
		return ResponseEntity.ok(response);
	}
}
