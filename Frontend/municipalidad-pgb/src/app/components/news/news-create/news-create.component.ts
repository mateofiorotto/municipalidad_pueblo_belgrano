import { Component } from '@angular/core';
import { NewsService } from '../../../services/news/news.service';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { inject } from '@angular/core';
import { CategoryResponseDTO } from '../../../models/category.model';
import { CategoriesService } from '../../../services/categories/categories.service';
import { EventsService } from '../../../services/events/events.service';
import { EventResponseDTO } from '../../../models/event.model';

@Component({
  selector: 'app-news-create',
  imports: [ReactiveFormsModule],
  templateUrl: './news-create.component.html',
  styleUrl: './news-create.component.css',
})
export class NewsCreateComponent {
  errors: { defaultMessage: string }[] = [];
  categoriesList: CategoryResponseDTO[] = [];
  eventsList: EventResponseDTO[] = [];

  private _newsService = inject(NewsService);
  private _categoriesService = inject(CategoriesService);
  private _eventsService = inject(EventsService);
  private _router = inject(Router);

  newsForm = new FormGroup({
    titular: new FormControl('', [
      Validators.required,
      Validators.minLength(15),
      Validators.maxLength(100),
    ]),
    subtitulo: new FormControl('', [
      Validators.required,
      Validators.minLength(20),
      Validators.maxLength(255),
    ]),
    fecha: new FormControl('', [Validators.required]),
    imagen: new FormControl('', [Validators.required]),
    descripcion: new FormControl('', [
      Validators.required,
      Validators.minLength(20),
      Validators.maxLength(1500),
    ]),
    descripcion_adicional: new FormControl('', [Validators.maxLength(1500)]),
    categoria: new FormControl('', [Validators.required]),
    evento: new FormControl('', []),
  });

  ngOnInit(): void {
    this.loadCategories();
    this.loadEvents();
  }

  onSubmit(): void {
    const val = this.newsForm.value;

    if (
      val.titular &&
      val.subtitulo &&
      val.fecha &&
      val.imagen &&
      val.descripcion &&
      val.categoria
    ) {
      const newsRequest: any = val;

      //parsear categoria y evento
      newsRequest.categoria = { id: newsRequest.categoria };
      newsRequest.evento = newsRequest.evento
        ? { id: newsRequest.evento }
        : null;

      this._newsService.createNews(newsRequest).subscribe({
        next: (data) => {
          this.errors = [];
          this.newsForm.reset();

          this._router.navigate(['/admin/noticias']).then(() => {
            Swal.fire({
              icon: 'success',
              title: 'Noticia creada',
              text: 'Noticia creada correctamente',
              showConfirmButton: true,
            });
          });
        },
        error: (err) => {
          if (err.error && Array.isArray(err.error.errors)) {
            this.errors = err.error.errors;

            const listaErrores = `
              <ul style="text-align:left">
                ${this.errors
                  .map((e) => `<li>${e.defaultMessage}</li>`)
                  .join('')}
              </ul>
            `;

            Swal.fire({
              icon: 'error',
              title: 'ERROR',
              html: listaErrores,
              showConfirmButton: true,
            });
          } else if (err.status == 400) {
            Swal.fire({
              icon: 'error',
              title: 'ERROR',
              text: err.error.message,
              showConfirmButton: true,
            });
          } else {
            Swal.fire({
              icon: 'error',
              title: 'ERROR',
              text: 'Ocurrio un error al crear la noticia. Lo estamos solucionando.',
              showConfirmButton: true,
            });
          }
        },
      });
    } else {
      Swal.fire({
        icon: 'error',
        title: 'ERROR',
        text: 'Todos los campos son requeridos',
        showConfirmButton: true,
      });
    }
  }

  public loadCategories(): CategoryResponseDTO[] {
    this._categoriesService.getCategoriesList().subscribe({
      next: (data) => {
        this.categoriesList = data.result;
      },
      error: (err) => {
        this._router.navigate(['/']).then(() => {
          Swal.fire({
            icon: 'error',
            title: 'ERROR',
            text: 'Error al cargar categorias o no estas autorizado/a',
            showConfirmButton: true,
          });
        });
      },
    });
    return this.categoriesList;
  }

  public loadEvents(): EventResponseDTO[] {
    this._eventsService.getEventsList().subscribe({
      next: (data) => {
        this.eventsList = data.result.content;
      },
      error: (err) => {
        this._router.navigate(['/']).then(() => {
          Swal.fire({
            icon: 'error',
            title: 'ERROR',
            text: 'Error al cargar eventos o no estas autorizado/a',
            showConfirmButton: true,
          });
        });
      },
    });
    return this.eventsList;
  }

  get titular() {
    return this.newsForm.get('titular');
  }

  get subtitulo() {
    return this.newsForm.get('subtitulo');
  }

  get fecha() {
    return this.newsForm.get('fecha');
  }

  get imagen() {
    return this.newsForm.get('imagen');
  }

  get descripcion() {
    return this.newsForm.get('descripcion');
  }

  get descripcion_adicional() {
    return this.newsForm.get('descripcion_adicional');
  }

  get categoria() {
    return this.newsForm.get('categoria');
  }

  get evento() {
    return this.newsForm.get('evento');
  }
}
