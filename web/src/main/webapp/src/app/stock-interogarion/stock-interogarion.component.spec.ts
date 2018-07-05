import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StockInterogarionComponent } from './stock-interogarion.component';

describe('StockInterogarionComponent', () => {
  let component: StockInterogarionComponent;
  let fixture: ComponentFixture<StockInterogarionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StockInterogarionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StockInterogarionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
