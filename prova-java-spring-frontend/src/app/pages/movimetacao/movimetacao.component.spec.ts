import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MovimetacaoComponent } from './movimetacao.component';

describe('MovimetacaoComponent', () => {
  let component: MovimetacaoComponent;
  let fixture: ComponentFixture<MovimetacaoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MovimetacaoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MovimetacaoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
